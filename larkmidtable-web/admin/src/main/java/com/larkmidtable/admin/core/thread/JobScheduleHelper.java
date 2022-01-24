package com.larkmidtable.admin.core.thread;

import com.larkmidtable.admin.core.conf.JobAdminConfig;
import com.larkmidtable.admin.core.cron.CronExpression;
import com.larkmidtable.admin.entity.JobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author xuxueli 2019-05-21
 */
public class JobScheduleHelper {
	public static final long PRE_READ_MS = 5000;    // pre read
	private static Logger logger = LoggerFactory.getLogger(JobScheduleHelper.class);
	private static JobScheduleHelper instance = new JobScheduleHelper();
	private volatile static Map<Integer, List<Integer>> ringData = new ConcurrentHashMap<>();
	private Thread scheduleThread;
	private Thread ringThread;
	private volatile boolean scheduleThreadToStop = false;
	private volatile boolean ringThreadToStop = false;

	public static JobScheduleHelper getInstance() {
		return instance;
	}

	public void start() {
		JobTriggerPoolHelper jobTriggerPoolHelper = new JobTriggerPoolHelper();
		scheduleThread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					TimeUnit.MILLISECONDS.sleep(5000 - System.currentTimeMillis() % 1000);
				} catch (InterruptedException e) {
					if (!scheduleThreadToStop) {
						logger.error(e.getMessage(), e);
					}
				}
				logger.info(">>>>>>>>> init web admin scheduler success.");

				int preReadCount =
						(JobAdminConfig.getAdminConfig().getTriggerPoolFastMax() + JobAdminConfig.getAdminConfig()
								.getTriggerPoolSlowMax()) * 20;
				while (!scheduleThreadToStop) {
					Connection conn = null;
					Boolean connAutoCommit = null;
					PreparedStatement preparedStatement = null;
					try {
						conn = JobAdminConfig.getAdminConfig().getDataSource().getConnection();
						connAutoCommit = conn.getAutoCommit();
						conn.setAutoCommit(false);
						preparedStatement = conn.prepareStatement(
								"select * from job_lock where lock_name = 'schedule_lock' for update");
						preparedStatement.execute();
						long nowTime = System.currentTimeMillis();
						List<JobInfo> scheduleList = JobAdminConfig.getAdminConfig().getJobInfoMapper()
								.scheduleJobQuery(nowTime , preReadCount);
						if (scheduleList != null && scheduleList.size() > 0) {
							for (JobInfo jobInfo : scheduleList) {
								jobTriggerPoolHelper.runJobWithJson(jobInfo.getJobJson());
								CronExpression cronExpression = new CronExpression(jobInfo.getJobCron());
								Date lastTime = new Date();
								lastTime = cronExpression.getNextValidTimeAfter(lastTime);
								//
								JobInfo jobInfo2 = JobAdminConfig.getAdminConfig().getJobInfoMapper().loadById(jobInfo.getId());
								jobInfo2.setTriggerNextTime(lastTime.getTime());
								JobAdminConfig.getAdminConfig().getJobInfoMapper().scheduleUpdate(jobInfo2);
							}
						}
					} catch (Exception e) {
						if (!scheduleThreadToStop) {
							logger.error(">>>>>>>>>>> web, JobScheduleHelper#scheduleThread error:{}", e);
						}
					} finally {
						// commit
						if (conn != null) {
							try {
								conn.commit();
							} catch (SQLException e) {
								if (!scheduleThreadToStop) {
									logger.error(e.getMessage(), e);
								}
							}
							try {
								conn.close();
							} catch (SQLException e) {
								if (!scheduleThreadToStop) {
									logger.error(e.getMessage(), e);
								}
							}
						}
						if (null != preparedStatement) {
							try {
								preparedStatement.close();
							} catch (SQLException e) {
								if (!scheduleThreadToStop) {
									logger.error(e.getMessage(), e);
								}
							}
						}
					}
				}
				logger.info(">>>>>>>>>>> web, JobScheduleHelper#scheduleThread stop");
			}
		});
		scheduleThread.setDaemon(true);
		scheduleThread.setName("web, admin JobScheduleHelper#scheduleThread");
		scheduleThread.start();


		// ring thread
		ringThread = new Thread(() -> {

			// align second
			try {
				TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
			} catch (InterruptedException e) {
				if (!ringThreadToStop) {
					logger.error(e.getMessage(), e);
				}
			}

			while (!ringThreadToStop) {

				try {
					// second data
					List<Integer> ringItemData = new ArrayList<>();
					int nowSecond = Calendar.getInstance().get(Calendar.SECOND);   // 避免处理耗时太长，跨过刻度，向前校验一个刻度；
					for (int i = 0; i < 2; i++) {
						List<Integer> tmpData = ringData.remove((nowSecond + 60 - i) % 60);
						if (tmpData != null) {
							ringItemData.addAll(tmpData);
						}
					}

					// ring trigger
					logger.debug(
							">>>>>>>>>>> web, time-ring beat : " + nowSecond + " = " + Arrays.asList(ringItemData));
					if (ringItemData.size() > 0) {
						// do trigger
						for (int jobId : ringItemData) {
							// do trigger
							jobTriggerPoolHelper.runJob(jobId);
						}
						// clear
						ringItemData.clear();
					}
				} catch (Exception e) {
					if (!ringThreadToStop) {
						logger.error(">>>>>>>>>>> web, JobScheduleHelper#ringThread error:{}", e);
					}
				}

				// next second, align second
				try {
					TimeUnit.MILLISECONDS.sleep(1000 - System.currentTimeMillis() % 1000);
				} catch (InterruptedException e) {
					if (!ringThreadToStop) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			logger.info(">>>>>>>>>>> web, JobScheduleHelper#ringThread stop");
		});
		ringThread.setDaemon(true);
		ringThread.setName("web, admin JobScheduleHelper#ringThread");
		ringThread.start();
	}

	private void refreshNextValidTime(JobInfo jobInfo, Date fromTime) throws ParseException {
		Date nextValidTime = new CronExpression(jobInfo.getJobCron()).getNextValidTimeAfter(fromTime);
		if (nextValidTime != null) {
			jobInfo.setTriggerLastTime(jobInfo.getTriggerNextTime());
			jobInfo.setTriggerNextTime(nextValidTime.getTime());
		} else {
			jobInfo.setTriggerStatus(0);
			jobInfo.setTriggerLastTime(0);
			jobInfo.setTriggerNextTime(0);
		}
	}

	private void pushTimeRing(int ringSecond, int jobId) {
		// push async ring
		List<Integer> ringItemData = ringData.get(ringSecond);
		if (ringItemData == null) {
			ringItemData = new ArrayList<Integer>();
			ringData.put(ringSecond, ringItemData);
		}
		ringItemData.add(jobId);

		logger.debug(">>>>>>>>>>> web, schedule push time-ring : " + ringSecond + " = " + Arrays.asList(ringItemData));
	}

	public void toStop() {

		// 1、stop schedule
		scheduleThreadToStop = true;
		try {
			TimeUnit.SECONDS.sleep(1);  // wait
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		if (scheduleThread.getState() != Thread.State.TERMINATED) {
			// interrupt and wait
			scheduleThread.interrupt();
			try {
				scheduleThread.join();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}

		// if has ring data
		boolean hasRingData = false;
		if (!ringData.isEmpty()) {
			for (int second : ringData.keySet()) {
				List<Integer> tmpData = ringData.get(second);
				if (tmpData != null && tmpData.size() > 0) {
					hasRingData = true;
					break;
				}
			}
		}
		if (hasRingData) {
			try {
				TimeUnit.SECONDS.sleep(8);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}

		// stop ring (wait job-in-memory stop)
		ringThreadToStop = true;
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		if (ringThread.getState() != Thread.State.TERMINATED) {
			// interrupt and wait
			ringThread.interrupt();
			try {
				ringThread.join();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}

		logger.info(">>>>>>>>>>> web, JobScheduleHelper stop");
	}

}
