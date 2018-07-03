package com.example.webfluxclient.utils.resthandles;

import com.example.webfluxclient.utils.beans.MethodInfo;
import com.example.webfluxclient.utils.beans.ServerInfo;
import com.example.webfluxclient.utils.interfaces.ProxyCreator;
import com.example.webfluxclient.utils.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 基于webclient的rest请求实现
 * @author Lucifer
 * @date 2018／07／03 22:44
 */
public class WebClientRestHandler implements RestHandler{

    private WebClient webClient;

    /**
     * @do 初始化webClient
     * @param serverInfo 服务器信息
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.webClient = WebClient.create(serverInfo.getUrl());
    }

    /**
     * @do 处理rest请求
     * @param methodInfo 方法信息
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        //创建返回的对象信息
        Object result = null;
        WebClient.RequestBodySpec request = this.webClient
                //设置请求的方法
                .method(methodInfo.getHttpMethod())
                //请求url
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                //指定返回数据的类型
                .accept(MediaType.APPLICATION_JSON);
                //执行该rest请求
        WebClient.ResponseSpec responseSpec = null;
        //判断是否带了body
        if (null != methodInfo.getBody()){
            //有body的时候,对body的信息进行封装
            responseSpec = request.body(methodInfo.getBody(), methodInfo.getBodyElementType()).retrieve();
        }else {
            //没有body的时候
            responseSpec = request.retrieve();
        }
        //处理异常
        responseSpec.onStatus(status -> status.value() == 404, response -> Mono.just(new RuntimeException("heiehheiehi")));
                //对返回的数据进行处理
        if (methodInfo.isReturnFlux()){
            result = responseSpec.bodyToFlux(methodInfo.getReturnElementType());
        }else{
            result = responseSpec.bodyToMono(methodInfo.getReturnElementType());
        }
        return result;
    }

}
