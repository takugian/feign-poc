package tg.com.feignpoc.core.reflection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import tg.com.feignpoc.feignclient.IPokeApiClient;

@SuppressWarnings("rawtypes")
public class InvocationHandlerImpl implements InvocationHandler {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36";

	public static void main(String args[]) {

		ClassLoader classLoader = IPokeApiClient.class.getClassLoader();
		Class[] interfaces = new Class[] { IPokeApiClient.class };

		IPokeApiClient client = (IPokeApiClient) Proxy.newProxyInstance(classLoader, interfaces,
				new InvocationHandlerImpl());

		System.out.println(client.obterPeloId(1));

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		RequestMapping classRM = method.getDeclaringClass().getAnnotation(RequestMapping.class);
		RequestMapping methodRM = method.getAnnotation(RequestMapping.class);
		Class<?> returnType = method.getReturnType();

		try {

			String restService = classRM.path()[0].toString() + methodRM.path()[0].toString();
			for (Parameter parameter : method.getParameters()) {
				PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
				restService = restService.replace("{" + pathVariable.value() + "}", args[0].toString());
			}
			for (String param : methodRM.params()) {
				if (restService.contains("?")) {
					restService = restService.concat("&").concat(param);
				} else {
					restService = restService.concat("?").concat(param);
				}
			}

			URL url = new URL(restService);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(methodRM.method()[0].toString());
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("user-agent", USER_AGENT);
			for (String header : methodRM.headers()) {
				String[] headersKeyValue = header.split("=");
				conn.setRequestProperty(headersKeyValue[0], headersKeyValue[1]);
			}

			if (conn.getResponseCode() >= 400) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.readValue(output, returnType);
			}

			conn.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
