package com.wugui.adminbiz;

import com.guoliang.flinkx.core.biz.AdminBiz;
import com.guoliang.flinkx.core.biz.client.AdminBizClient;
import com.guoliang.flinkx.core.biz.model.HandleCallbackParam;
import com.guoliang.flinkx.core.biz.model.RegistryParam;
import com.guoliang.flinkx.core.biz.model.ReturnT;
import com.guoliang.flinkx.core.enums.RegistryConfig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
public class AdminBizTest {

    // admin-client
    private static String addressUrl = "http://127.0.0.1:8080/datax-admin/";
    private static String accessToken = null;


    @Test
    public void callback() throws Exception {
        AdminBiz adminBiz = new AdminBizClient(addressUrl, accessToken);

        HandleCallbackParam param = new HandleCallbackParam();
        param.setLogId(1);
        param.setExecuteResult(ReturnT.SUCCESS);

        List<HandleCallbackParam> callbackParamList = Arrays.asList(param);

        ReturnT<String> returnT = adminBiz.callback(callbackParamList);

        Assert.assertTrue(returnT.getCode() == ReturnT.SUCCESS_CODE);
    }

    /**
     * registry executor
     *
     * @throws Exception
     */
    @Test
    public void registry() throws Exception {
        AdminBiz adminBiz = new AdminBizClient(addressUrl, accessToken);

        RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), "flinkx-web-executor-example", "127.0.0.1:9999");
        ReturnT<String> returnT = adminBiz.registry(registryParam);

        Assert.assertTrue(returnT.getCode() == ReturnT.SUCCESS_CODE);
    }

    /**
     * registry executor remove
     *
     * @throws Exception
     */
    @Test
    public void registryRemove() throws Exception {
        AdminBiz adminBiz = new AdminBizClient(addressUrl, accessToken);

        RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), "flinkx-web-executor-example", "127.0.0.1:9999");
        ReturnT<String> returnT = adminBiz.registryRemove(registryParam);

        Assert.assertTrue(returnT.getCode() == ReturnT.SUCCESS_CODE);

    }

}
