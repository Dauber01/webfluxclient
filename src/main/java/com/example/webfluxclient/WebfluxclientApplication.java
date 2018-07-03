package com.example.webfluxclient;

import com.example.webfluxclient.apis.IUserApi;
import com.example.webfluxclient.utils.interfaces.ProxyCreator;
import com.example.webfluxclient.utils.proxys.JDKProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

@SpringBootApplication
public class WebfluxclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxclientApplication.class, args);
	}

	@Bean
	FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator){
		return new FactoryBean<IUserApi>() {
			@Nullable
			@Override
			public IUserApi getObject() throws Exception {
				return (IUserApi) proxyCreator.createProxy(this.getObjectType());
			}

			@Nullable
			@Override
			public Class<?> getObjectType() {
				return IUserApi.class;
			}
		};
	}

	/**
	 * @do 创建jdk代理工具类
	 * @return
	 */
	@Bean
	ProxyCreator jdkProxyCreator(){
		return new JDKProxyCreator();
	}

}
