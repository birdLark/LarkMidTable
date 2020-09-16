package com.wugui.executor;

import com.guoliang.flinkx.core.biz.ExecutorBiz;
import com.guoliang.flinkx.core.biz.model.ReturnT;
import com.guoliang.flinkx.core.biz.model.TriggerParam;
import com.guoliang.flinkx.core.enums.ExecutorBlockStrategyEnum;
import com.guoliang.flinkx.core.glue.GlueTypeEnum;
import com.guoliang.flinkx.rpc.remoting.invoker.XxlRpcInvokerFactory;
import com.guoliang.flinkx.rpc.remoting.invoker.call.CallType;
import com.guoliang.flinkx.rpc.remoting.invoker.reference.XxlRpcReferenceBean;
import com.guoliang.flinkx.rpc.remoting.invoker.route.LoadBalance;
import com.guoliang.flinkx.rpc.remoting.net.impl.netty_http.client.NettyHttpClient;
import com.guoliang.flinkx.rpc.serialize.impl.HessianSerializer;

/**
 * executor-api client, test
 *
 * Created by xuxueli on 17/5/12.
 */
public class ExecutorBizTest {

    public static void main(String[] args) throws Exception {

        // param
        String jobHandler = "demoJobHandler";
        String params = "";

        runTest(jobHandler, params);
    }

    /**
     * run jobhandler
     *
     * @param jobHandler
     * @param params
     */
    private static void runTest(String jobHandler, String params) throws Exception {
        // trigger data
        TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(1);
        triggerParam.setExecutorHandler(jobHandler);
        triggerParam.setExecutorParams(params);
        triggerParam.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.COVER_EARLY.name());
        triggerParam.setGlueType(GlueTypeEnum.BEAN.name());
        triggerParam.setGlueSource(null);
        triggerParam.setGlueUpdatetime(System.currentTimeMillis());
        triggerParam.setLogId(1);
        triggerParam.setLogDateTime(System.currentTimeMillis());

        // do remote trigger
        String accessToken = null;

        XxlRpcReferenceBean referenceBean = new XxlRpcReferenceBean();
        referenceBean.setClient(NettyHttpClient.class);
        referenceBean.setSerializer(HessianSerializer.class);
        referenceBean.setCallType(CallType.SYNC);
        referenceBean.setLoadBalance(LoadBalance.ROUND);
        referenceBean.setIface(ExecutorBiz.class);
        referenceBean.setVersion(null);
        referenceBean.setTimeout(3000);
        referenceBean.setAddress("127.0.0.1:9999");
        referenceBean.setAccessToken(accessToken);
        referenceBean.setInvokeCallback(null);
        referenceBean.setInvokerFactory(null);

        ExecutorBiz executorBiz = (ExecutorBiz) referenceBean.getObject();

        ReturnT<String> runResult = executorBiz.run(triggerParam);

        System.out.println(runResult);
        XxlRpcInvokerFactory.getInstance().stop();
    }

}
