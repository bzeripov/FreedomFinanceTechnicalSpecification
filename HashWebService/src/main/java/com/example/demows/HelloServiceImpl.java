package com.example.demows;

import javax.jws.WebService;
import java.util.Arrays;

@WebService(
		serviceName = "Hello",
		portName = "HelloPort",
		targetNamespace = "http://service.ws.sample/",
		endpointInterface = "com.example.demows.HelloService")
public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(byte[] file) {
		return String.valueOf(Arrays.hashCode(file));
	}

}
