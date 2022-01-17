package com.larkmidtable.executor.service.command;

import com.larkmidtable.executor.service.jobhandler.FlinkXConstant;
import com.larkmidtable.core.biz.model.TriggerParam;
import com.larkmidtable.core.enums.IncrementTypeEnum;
import com.larkmidtable.core.log.JobLogger;
import com.larkmidtable.core.util.Constants;
import com.larkmidtable.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.larkmidtable.core.util.Constants.SPLIT_COMMA;

/**
 * FlinkX command build
 *
 * @author jingwk 2020-06-07
 */
public class BuildCommand {

	private static final Logger LOGGER = LoggerFactory.getLogger(BuildCommand.class);

	/**
     * FlinkX command build
     * @param tgParam
     * @param tmpFilePath
     * @param flinkXShPath
     * @return
     */
    public static String[] buildFlinkXExecutorCmd(TriggerParam tgParam, String tmpFilePath, String flinkXShPath) {
        // command process
        //"--loglevel=debug"
        List<String> cmdArr = new ArrayList<>();
        cmdArr.add("sh");
        cmdArr.add(flinkXShPath);
        cmdArr.add(tmpFilePath);
		LOGGER.info(cmdArr+" "+flinkXShPath+" "+tmpFilePath);
        return cmdArr.toArray(new String[cmdArr.size()]);
    }

    private static String buildFlinkXParam(TriggerParam tgParam) {
        StringBuilder doc = new StringBuilder();
        String jvmParam = StringUtils.isNotBlank(tgParam.getJvmParam()) ? tgParam.getJvmParam().trim() : tgParam.getJvmParam();
        String partitionStr = tgParam.getPartitionInfo();
        if (StringUtils.isNotBlank(jvmParam)) {
            doc.append(FlinkXConstant.JVM_CM).append(FlinkXConstant.TRANSFORM_QUOTES).append(jvmParam).append(
					FlinkXConstant.TRANSFORM_QUOTES);
        }

        Integer incrementType = tgParam.getIncrementType();
        String replaceParam = StringUtils.isNotBlank(tgParam.getReplaceParam()) ? tgParam.getReplaceParam().trim() : null;

        if (incrementType != null && replaceParam != null) {

            if (IncrementTypeEnum.TIME.getCode() == incrementType) {
                if (doc.length() > 0) doc.append(FlinkXConstant.SPLIT_SPACE);
                String replaceParamType = tgParam.getReplaceParamType();

                if (StringUtils.isBlank(replaceParamType) || replaceParamType.equals("Timestamp")) {
                    long startTime = tgParam.getStartTime().getTime() / 1000;
                    long endTime = tgParam.getTriggerTime().getTime() / 1000;
                    doc.append(FlinkXConstant.PARAMS_CM).append(FlinkXConstant.TRANSFORM_QUOTES).append(String.format(replaceParam, startTime, endTime));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(replaceParamType);
                    String endTime = sdf.format(tgParam.getTriggerTime()).replaceAll(FlinkXConstant.SPLIT_SPACE, FlinkXConstant.PERCENT);
                    String startTime = sdf.format(tgParam.getStartTime()).replaceAll(FlinkXConstant.SPLIT_SPACE, FlinkXConstant.PERCENT);
                    doc.append(FlinkXConstant.PARAMS_CM).append(FlinkXConstant.TRANSFORM_QUOTES).append(String.format(replaceParam, startTime, endTime));
                }
                //buildPartitionCM(doc, partitionStr);
                doc.append(FlinkXConstant.TRANSFORM_QUOTES);

            } else if (IncrementTypeEnum.ID.getCode() == incrementType) {
                long startId = tgParam.getStartId();
                long endId = tgParam.getEndId();
                if (doc.length() > 0) doc.append(FlinkXConstant.SPLIT_SPACE);
                doc.append(FlinkXConstant.PARAMS_CM).append(FlinkXConstant.TRANSFORM_QUOTES).append(String.format(replaceParam, startId, endId));
                doc.append(FlinkXConstant.TRANSFORM_QUOTES);
            }
        }

        if (incrementType != null && IncrementTypeEnum.PARTITION.getCode() == incrementType) {
            if (StringUtils.isNotBlank(partitionStr)) {
                List<String> partitionInfo = Arrays.asList(partitionStr.split(SPLIT_COMMA));
                if (doc.length() > 0) doc.append(FlinkXConstant.SPLIT_SPACE);
                doc.append(FlinkXConstant.PARAMS_CM).append(FlinkXConstant.TRANSFORM_QUOTES).append(String.format(
						FlinkXConstant.PARAMS_CM_V_PT, buildPartition(partitionInfo))).append(
						FlinkXConstant.TRANSFORM_QUOTES);
            }
        }

        JobLogger.log("------------------Command parameters:" + doc);
        return doc.toString();
    }


    private void buildPartitionCM(StringBuilder doc, String partitionStr) {
        if (StringUtils.isNotBlank(partitionStr)) {
            doc.append(FlinkXConstant.SPLIT_SPACE);
            List<String> partitionInfo = Arrays.asList(partitionStr.split(SPLIT_COMMA));
            doc.append(String.format(FlinkXConstant.PARAMS_CM_V_PT, buildPartition(partitionInfo)));
        }
    }

    private static String buildPartition(List<String> partitionInfo) {
        String field = partitionInfo.get(0);
        int timeOffset = Integer.parseInt(partitionInfo.get(1));
        String timeFormat = partitionInfo.get(2);
        String partitionTime = DateUtil.format(DateUtil.addDays(new Date(), timeOffset), timeFormat);
        return field + Constants.EQUAL + partitionTime;
    }

}
