package com.example.webfluxclient.utils.proxys;

import com.example.webfluxclient.utils.ApiServer;
import com.example.webfluxclient.utils.beans.MethodInfo;
import com.example.webfluxclient.utils.beans.ServerInfo;
import com.example.webfluxclient.utils.interfaces.ProxyCreator;
import com.example.webfluxclient.utils.interfaces.RestHandler;
import com.example.webfluxclient.utils.resthandles.WebClientRestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 利用jdk调用proxy的接口
 *
 * @author Lucifer
 * @date 2018／07／03 20:36
 */
@Slf4j
public class JDKProxyCreator implements ProxyCreator{

    @Override
    public Object createProxy(Class<?> type) {
        log.info("createProxy , type : " + type);
        //根据接口得到API服务器信息
        ServerInfo serverInfo = extractServerInfo(type);
        log.info("createProxy , serverInfo : " + serverInfo);
        //给每个代理类一个实现
        RestHandler restHandler = new WebClientRestHandler();
        restHandler.init(serverInfo);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{type}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //根据方法和参数得到调用信息
                        MethodInfo methodInfo = extractMethodInfo(method, args);
                        log.info("createProxy , methodInfo : " + methodInfo);
                        //调用rest
                        return restHandler.invokeRest(methodInfo);
                    }

                    /**
                     * @do 根据调用的方式和方法的参数,得到调用的信息
                     * @param method
                     * @param args
                     * @return
                     */
                    public MethodInfo extractMethodInfo(Method method, Object[] args){
                        MethodInfo methodInfo = new MethodInfo();
                        //得到请求的url和请求的方法
                        extractUrlAndMethod(method, methodInfo);
                        //得到调用的参数和body
                        extractParamsAndBody(method, args, methodInfo);
                        //提取返回对象的信息
                        extractReturnInfo(method, methodInfo);
                        return methodInfo;
                    }

                });
    }

    /**
     * @do 提取返回对象的信息
     * @param method
     * @param methodInfo
     */
    private void extractReturnInfo(Method method, MethodInfo methodInfo){
        //判断返回的是Flux还是Mono
        //isAssignableFrom判断类型是否是某个的子类,而instanceof判断的是实例是否是某个的子类
        Boolean returnType = method.getReturnType().isAssignableFrom(Flux.class);
        methodInfo.setReturnFlux(returnType);
        //得到返回对象的实际类型
        Class<?> returnElementType = extractElementType(method.getGenericReturnType());
        methodInfo.setReturnElementType(returnElementType);
    }

    /**
     * @do 得到泛型的实际类型
     * @param type
     * @return
     */
    public Class<?> extractElementType(Type type){
        //得到泛型的实际类型
        Type[] types = ((ParameterizedType)type).getActualTypeArguments();
        return (Class<?>) types[0];
    }

    /**
     * @do 提取服务器信息
     * @param type
     * @return
     */
    public ServerInfo extractServerInfo(Class<?> type){
        ServerInfo serverInfo = new ServerInfo();
        ApiServer apiServer = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(apiServer.value());
        return serverInfo;
    }

    /**
     * @do 得到请求的url和请求的方法
     * @param method
     * @param methodInfo
     */
    private void extractUrlAndMethod(Method method, MethodInfo methodInfo){
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            //Get方法
            if (annotation instanceof GetMapping){
                GetMapping getMapping = (GetMapping)annotation;
                methodInfo.setUrl(getMapping.value()[0]);
                methodInfo.setHttpMethod(HttpMethod.GET);
            }else if (annotation instanceof PostMapping){
                //post方法
                PostMapping postMapping = (PostMapping)annotation;
                methodInfo.setUrl(postMapping.value()[0]);
                methodInfo.setHttpMethod(HttpMethod.POST);
            }else if (annotation instanceof DeleteMapping){
                //delete方法
                DeleteMapping deleteMapping = (DeleteMapping)annotation;
                methodInfo.setUrl(deleteMapping.value()[0]);
                methodInfo.setHttpMethod(HttpMethod.DELETE);
            }
        }
    }

    /**
     * @do 得到调用的参数和body
     * @param method
     * @param args
     * @param methodInfo
     */
    private void extractParamsAndBody(Method method, Object[] args, MethodInfo methodInfo){
        Parameter[] parameters = method.getParameters();
        //参数和值对应的map
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        methodInfo.setParams(params);
        for (int i = 0; i < parameters.length; i++) {
            //参数是否带pathVariable
            PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
            if (null != pathVariable){
                params.put(pathVariable.value(), args[i]);
            }
            //参数是否带了requestBody
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (null != requestBody){
                methodInfo.setBody((Mono<?>)args[i]);
                //请求对象的实体类型
                methodInfo.setBodyElementType(extractElementType(parameters[i].getParameterizedType()));
            }
        }
    }

}
