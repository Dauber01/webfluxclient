package com.example.webfluxclient.web;

import com.example.webfluxclient.apis.ApiServer;
import com.example.webfluxclient.apis.IUserApi;
import com.example.webfluxclient.model.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Lucifer
 * @do 测试controller
 * @date 2018/07/03 14:19
 */
@RestController
public class TestController {

    @Autowired
    private IUserApi iUserApi;

    @GetMapping("/")
    public void test(){
        Flux<MyEvent> myEventFlux = iUserApi.findAllMyEvent();
        myEventFlux.subscribe(System.out::println);
    }

}
