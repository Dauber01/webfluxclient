package com.example.webfluxclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lucifer
 * @do 长连接的响应bean
 * @date 2018/06/21 11:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyEvent {

    private Long id;

    private String message;

}
