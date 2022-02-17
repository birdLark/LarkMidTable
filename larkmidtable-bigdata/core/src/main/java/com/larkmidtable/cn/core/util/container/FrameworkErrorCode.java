package com.larkmidtable.cn.core.util.container;

import com.larkmidtable.common.spi.ErrorCode;

/**
 * TODO: 根据现有日志数据分析各类错误，进行细化。
 *
 * <p>请不要格式化本类代码</p>
 */
public enum FrameworkErrorCode implements ErrorCode {

	INSTALL_ERROR("Framework-00", "LarkMidTable引擎安装错误, 请联系您的运维解决 ."),
	ARGUMENT_ERROR("Framework-01", "LarkMidTable引擎运行错误，该问题通常是由于内部编程错误引起，请联系LarkMidTable开发团队解决 ."),
	RUNTIME_ERROR("Framework-02", "LarkMidTable引擎运行过程出错，具体原因请参看LarkMidTable运行结束时的错误诊断信息  ."),
	CONFIG_ERROR("Framework-03", "LarkMidTable引擎配置错误，该问题通常是由于LarkMidTable安装错误引起，请联系您的运维解决 ."),
    SECRET_ERROR("Framework-04", "LarkMidTable引擎加解密出错，该问题通常是由于LarkMidTable密钥配置错误引起，请联系您的运维解决 ."),
    HOOK_LOAD_ERROR("Framework-05", "加载外部Hook出现错误，通常是由于LarkMidTable安装引起的"),
    HOOK_FAIL_ERROR("Framework-06", "执行外部Hook出现错误"),

    PLUGIN_INSTALL_ERROR("Framework-10", "LarkMidTable插件安装错误, 该问题通常是由于LarkMidTable安装错误引起，请联系您的运维解决 ."),
    PLUGIN_NOT_FOUND("Framework-11", "LarkMidTable插件配置错误, 该问题通常是由于LarkMidTable安装错误引起，请联系您的运维解决 ."),
    PLUGIN_INIT_ERROR("Framework-12", "LarkMidTable插件初始化错误, 该问题通常是由于LarkMidTable安装错误引起，请联系您的运维解决 ."),
    PLUGIN_RUNTIME_ERROR("Framework-13", "LarkMidTable插件运行时出错, 具体原因请参看LarkMidTable运行结束时的错误诊断信息 ."),
    PLUGIN_DIRTY_DATA_LIMIT_EXCEED("Framework-14", "LarkMidTable传输脏数据超过用户预期，该错误通常是由于源端数据存在较多业务脏数据导致，请仔细检查LarkMidTable汇报的脏数据日志信息, 或者您可以适当调大脏数据阈值 ."),
    PLUGIN_SPLIT_ERROR("Framework-15", "LarkMidTable插件切分出错, 该问题通常是由于LarkMidTable各个插件编程错误引起，请联系LarkMidTable开发团队解决"),
    KILL_JOB_TIMEOUT_ERROR("Framework-16", "kill 任务超时，请联系PE解决"),
    START_TASKGROUP_ERROR("Framework-17", "taskGroup启动失败,请联系LarkMidTable开发团队解决"),
    CALL_DATAX_SERVICE_FAILED("Framework-18", "请求 LarkMidTable Service 出错."),
    CALL_REMOTE_FAILED("Framework-19", "远程调用失败"),
    KILLED_EXIT_VALUE("Framework-143", "Job 收到了 Kill 命令.");

    private final String code;

    private final String description;

    private FrameworkErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("Code:[%s], Description:[%s]. ", this.code,
                this.description);
    }

    /**
     * 通过 "Framework-143" 来标示 任务是 Killed 状态
     */
    public int toExitValue() {
        if (this == FrameworkErrorCode.KILLED_EXIT_VALUE) {
            return 143;
        } else {
            return 1;
        }
    }

}
