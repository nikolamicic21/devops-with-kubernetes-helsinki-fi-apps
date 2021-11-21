package io.nikolamicic21.simpletodoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SimpleTodoAppApplication {

	public static void main(String[] args) {
		final var context = SpringApplication.run(SimpleTodoAppApplication.class, args);
		final var env = context.getEnvironment();
		System.out.println("Web server started on port: " + env.getProperty("local.server.port"));
	}

}
