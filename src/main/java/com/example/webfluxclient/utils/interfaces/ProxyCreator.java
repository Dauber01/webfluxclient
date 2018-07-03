package com.example.webfluxclient.utils.interfaces;

/**
 * 创建代理类的接口
 * @author Lucifer
 * @date 2018／07／03 20:30
 */
public interface ProxyCreator {

    /**
     * @do 创建代理类的接口
     * @param type
     * @return
     */
    Object createProxy(Class<?> type);

}
