package com.example.webfluxclient.web;

import com.example.webfluxclient.apis.IUserApi;
import com.example.webfluxclient.model.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Lucifer
 * @do 测试controller
 * @date 2018/07/03 14:19
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private IUserApi iUserApi;

    @GetMapping("/")
    public void test(){
        //测试信息的提取
        //不订阅消息,不会实际发送请求,但会进入代理类
        /*iUserApi.findAllMyEvent();
        iUserApi.findMyEventById("11111");
        iUserApi.deleteMyEventById("22222");
        iUserApi.createMyEvent(Mono.just(MyEvent.builder().id(111L).message("hello ,hello").build()));*/
        //Mono<MyEvent> myEventFlux = iUserApi.findMyEventById("3");
        /*Flux<MyEvent> myEventFlux = iUserApi.findAllMyEvent();
        myEventFlux.subscribe(System.out::println);*/
        /*iUserApi.createMyEvent(Mono.just(MyEvent.builder().id(666L).message("你好").build()))
                .subscribe(System.out::println);*/
        iUserApi.findMyEventById("3").subscribe(u -> {
            System.out.println(u);
        }, e -> {
            log.error("【框架调用接口】异常 e : " + e);
        });
    }

}
