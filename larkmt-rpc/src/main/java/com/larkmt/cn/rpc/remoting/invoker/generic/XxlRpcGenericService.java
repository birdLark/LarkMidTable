package com.larkmt.cn.rpc.remoting.invoker.generic;

/**
 *
 * @Author: LarkMidTable
 * @Date: 2020/9/16 11:14
 * @Description:
 **/
public interface XxlRpcGenericService {

    /**
     * generic invoke
     *
     * @param iface          iface name
     * @param version        iface version
     * @param method         method name
     * @param parameterTypes parameter types, limit base type like "int、java.lang.Integer、java.util.List、java.util.Map ..."
     * @param args
     * @return
     */
    Object invoke(String iface, String version, String method, String[] parameterTypes, Object[] args);

}