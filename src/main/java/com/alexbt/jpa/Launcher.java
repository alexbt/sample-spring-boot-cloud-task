package com.alexbt.jpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableTask
public class Launcher {

	@Autowired
	private DataSource dataSource;

	@Bean
	public TaskConfigurer taskConfigurer() {
		return new DefaultTaskConfigurer(dataSource);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return new MyTaskCommandLineRunner();
	}
	
	@Bean
	public TaskExecutionListener taskExecutionListener(){
		return new MyTaskExecutionListener();
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder() //
				.sources(Launcher.class)//
				.web(false)//
				.run("some", "parameters");
	}
}
