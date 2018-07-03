package com.example.webfluxclient.utils.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 方法调用信息类
 * @author Lucifer
 * @date 2018／07／03 20:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {

    /** 请求的url. */
    private String url;

    /** 请求的方法. */
    private HttpMethod httpMethod;

    /** 请求的参数. */
    private Map<String, Object> params;

    /** 请求体. */
    private Mono body;

    /** 请求body的类型. */
    private Class<?> bodyElementType;

    /** 返回的是flux还是mono. */
    private boolean returnFlux;

    /** 返回数据对象的类型. */
    private Class<?> returnElementType;

}
