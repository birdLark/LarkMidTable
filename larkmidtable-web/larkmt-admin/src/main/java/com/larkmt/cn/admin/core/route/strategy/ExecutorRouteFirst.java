package com.larkmt.cn.admin.core.route.strategy;

import com.larkmt.core.biz.model.ReturnT;
import com.larkmt.core.biz.model.TriggerParam;
import com.larkmt.cn.admin.core.route.ExecutorRouter;

import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteFirst extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList){
        return new ReturnT<String>(addressList.get(0));
    }

}
