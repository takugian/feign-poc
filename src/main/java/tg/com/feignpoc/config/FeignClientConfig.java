package tg.com.feignpoc.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tg.com.feignpoc.core.reflection.InvocationHandlerImpl;
import tg.com.feignpoc.feignclient.IPokeApiClient;

@SuppressWarnings("rawtypes")
@Configuration
public class FeignClientConfig {

	private InvocationHandler invocationHandler = new InvocationHandlerImpl();

	@Bean
	@Qualifier("feignClient")
	public IPokeApiClient produceFeignClient() {
		ClassLoader classLoader = IPokeApiClient.class.getClassLoader();
		Class[] interfaces = new Class[] { IPokeApiClient.class };
		return (IPokeApiClient) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
	}

}
