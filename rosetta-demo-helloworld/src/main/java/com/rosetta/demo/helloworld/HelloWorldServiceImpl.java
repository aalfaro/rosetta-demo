package com.rosetta.demo.helloworld;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;

@Component(immediate=true, label="Hello World Service")
@Properties({
	@Property(name=Constants.SERVICE_DESCRIPTION, value="Hello World Service"),
	@Property(name=Constants.SERVICE_VENDOR, value="Rosetta"),
	@Property(name="process.label", value="Hello World Service")
})
@Service
public class HelloWorldServiceImpl implements HelloWorldService {

	public String sayHello(String name) {
		return "Hello World " + name;
	}

}