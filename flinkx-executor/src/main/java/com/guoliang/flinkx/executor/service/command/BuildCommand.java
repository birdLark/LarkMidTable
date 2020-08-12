package com.guoliang.flinkx.executor.service.command;

import com.guoliang.flinkx.executor.service.jobhandler.DataXConstant;
import com.wugui.datatx.core.biz.model.TriggerParam;
import com.wugui.datatx.core.enums.IncrementTypeEnum;
import com.wugui.datatx.core.log.JobLogger;
import com.wugui.datatx.core.util.Constants;
import com.wugui.datatx.core.util.DateUtil;
import com.guoliang.flinkx.executor.util.SystemUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.wugui.datatx.core.util.Constants.SPLIT_COMMA;

/**
 * DataX command build
 *
 * @author jingwk 2020-06-07
 */
public class BuildCommand {

    /**
     * DataX command build
     * @param tgParam
     * @param tmpFilePath
     * @param dataXPyPath
     * @return
     */
    public static String[] buildDataXExecutorCmd(TriggerParam tgParam, String tmpFilePath, String dataXPyPath) {
        // command process
        //"--loglevel=debug"
        List<String> cmdArr = new ArrayList<>();
        cmdArr.add("python");
        String dataXHomePath = SystemUtils.getDataXHomePath();
        if (StringUtils.isNotEmpty(dataXHomePath)) {
            dataXPyPath = dataXHomePath.contains("bin") ? dataXHomePath + DataXConstant.DEFAULT_DATAX_PY : dataXHomePath + "bin" + File.separator + DataXConstant.DEFAULT_DATAX_PY;
        }
        cmdArr.add(dataXPyPath);
        String doc = buildDataXParam(tgParam);
        if (StringUtils.isNotBlank(doc)) {
            cmdArr.add(doc.replaceAll(DataXConstant.SPLIT_SPACE, DataXConstant.TRANSFORM_SPLIT_SPACE));
        }
        cmdArr.add(tmpFilePath);
        return cmdArr.toArray(new String[cmdArr.size()]);
    }

    private static String buildDataXParam(TriggerParam tgParam) {
        StringBuilder doc = new StringBuilder();
        String jvmParam = StringUtils.isNotBlank(tgParam.getJvmParam()) ? tgParam.getJvmParam().trim() : tgParam.getJvmParam();
        String partitionStr = tgParam.getPartitionInfo();
        if (StringUtils.isNotBlank(jvmParam)) {
            doc.append(DataXConstant.JVM_CM).append(DataXConstant.TRANSFORM_QUOTES).append(jvmParam).append(
					DataXConstant.TRANSFORM_QUOTES);
        }

        Integer incrementType = tgParam.getIncrementType();
        String replaceParam = StringUtils.isNotBlank(tgParam.getReplaceParam()) ? tgParam.getReplaceParam().trim() : null;

        if (incrementType != null && replaceParam != null) {

            if (IncrementTypeEnum.TIME.getCode() == incrementType) {
                if (doc.length() > 0) doc.append(DataXConstant.SPLIT_SPACE);
                String replaceParamType = tgParam.getReplaceParamType();

                if (StringUtils.isBlank(replaceParamType) || replaceParamType.equals("Timestamp")) {
                    long startTime = tgParam.getStartTime().getTime() / 1000;
                    long endTime = tgParam.getTriggerTime().getTime() / 1000;
                    doc.append(DataXConstant.PARAMS_CM).append(DataXConstant.TRANSFORM_QUOTES).append(String.format(replaceParam, startTime, endTime));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(replaceParamType);
                    String endTime = sdf.format(tgParam.getTriggerTime()).replaceAll(DataXConstant.SPLIT_SPACE, DataXConstant.PERCENT);
                    String startTime = sdf.format(tgParam.getStartTime()).replaceAll(DataXConstant.SPLIT_SPACE, DataXConstant.PERCENT);
                    doc.append(DataXConstant.PARAMS_CM).append(DataXConstant.TRANSFORM_QUOTES).append(String.format(replaceParam, startTime, endTime));
                }
                //buildPartitionCM(doc, partitionStr);
                doc.append(DataXConstant.TRANSFORM_QUOTES);

            } else if (IncrementTypeEnum.ID.getCode() == incrementType) {
                long startId = tgParam.getStartId();
                long endId = tgParam.getEndId();
                if (doc.length() > 0) doc.append(DataXConstant.SPLIT_SPACE);
                doc.append(DataXConstant.PARAMS_CM).append(DataXConstant.TRANSFORM_QUOTES).append(String.format(replaceParam, startId, endId));
                doc.append(DataXConstant.TRANSFORM_QUOTES);
            }
        }

        if (incrementType != null && IncrementTypeEnum.PARTITION.getCode() == incrementType) {
            if (StringUtils.isNotBlank(partitionStr)) {
                List<String> partitionInfo = Arrays.asList(partitionStr.split(SPLIT_COMMA));
                if (doc.length() > 0) doc.append(DataXConstant.SPLIT_SPACE);
                doc.append(DataXConstant.PARAMS_CM).append(DataXConstant.TRANSFORM_QUOTES).append(String.format(
						DataXConstant.PARAMS_CM_V_PT, buildPartition(partitionInfo))).append(
						DataXConstant.TRANSFORM_QUOTES);
            }
        }

        JobLogger.log("------------------Command parameters:" + doc);
        return doc.toString();
    }


    private void buildPartitionCM(StringBuilder doc, String partitionStr) {
        if (StringUtils.isNotBlank(partitionStr)) {
            doc.append(DataXConstant.SPLIT_SPACE);
            List<String> partitionInfo = Arrays.asList(partitionStr.split(SPLIT_COMMA));
            doc.append(String.format(DataXConstant.PARAMS_CM_V_PT, buildPartition(partitionInfo)));
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
