package com.example.webfluxclient.apis;

import com.example.webfluxclient.model.MyEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Lucifer
 * @do 用于直接调用webflux接口的声明式客户端
 * @date 2018/07/03 9:23
 */
@ApiServer("http://localhost:8080")
public interface IUserApi {

    @GetMapping("/")
    Flux<MyEvent> findAllMyEvent();

    @GetMapping("/{id}")
    Mono<MyEvent> findMyEventById(@PathVariable("id") String id);

    @DeleteMapping("/{id}")
    Mono<Void> deleteMyEventById(@PathVariable("id") String id);

    @PostMapping("/")
    Mono<MyEvent> createMyEvent(@RequestBody MyEvent myEvent);

}
