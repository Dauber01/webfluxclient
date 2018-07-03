package com.example.webfluxclient.utils.interfaces;

import com.example.webfluxclient.utils.beans.MethodInfo;
import com.example.webfluxclient.utils.beans.ServerInfo;

/**
 * rest接口调用handler
 * @author Lucifer
 * @date 2018／07／03 20:52
 */
public interface RestHandler {

    /**
     * @do 初始化服务器信息
     * @param serverInfo 服务器信息
     */
    void init(ServerInfo serverInfo);

    /**
     * @do 调用rest请求,返回接口
     * @param methodInfo 方法信息
     * @return 接口
     */
    Object invokeRest(MethodInfo methodInfo);

}
