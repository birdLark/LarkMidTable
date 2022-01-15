package com.guoliang.flinkx.core.biz.client;

import com.guoliang.flinkx.core.biz.model.HandleCallbackParam;
import com.guoliang.flinkx.core.biz.model.HandleProcessCallbackParam;
import com.guoliang.flinkx.core.biz.model.RegistryParam;
import com.guoliang.flinkx.core.util.JobRemotingUtil;
import com.guoliang.flinkx.core.biz.AdminBiz;
import com.guoliang.flinkx.core.biz.model.ReturnT;

import java.util.List;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
public class AdminBizClient implements AdminBiz {

    public AdminBizClient() {
    }
    public AdminBizClient(String addressUrl, String accessToken) {
        this.addressUrl = addressUrl;
        this.accessToken = accessToken;

        // valid
        if (!this.addressUrl.endsWith("/")) {
            this.addressUrl = this.addressUrl + "/";
        }
    }

    private String addressUrl ;
    private String accessToken;


    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return JobRemotingUtil.postBody(addressUrl+"api/callback", accessToken, callbackParamList, 3);
    }

    @Override
    public ReturnT<String> processCallback(List<HandleProcessCallbackParam> callbackParamList) {
        return JobRemotingUtil.postBody(addressUrl + "api/processCallback", accessToken, callbackParamList, 3);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return JobRemotingUtil.postBody(addressUrl + "api/registry", accessToken, registryParam, 3);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return JobRemotingUtil.postBody(addressUrl + "api/registryRemove", accessToken, registryParam, 3);
    }
}
