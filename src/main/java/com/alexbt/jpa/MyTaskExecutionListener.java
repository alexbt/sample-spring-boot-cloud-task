package com.alexbt.jpa;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.cloud.task.repository.TaskExplorer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

public class MyTaskExecutionListener implements TaskExecutionListener {
	
	private PageRequest pageRequest = new PageRequest(0,10);
	
	@Autowired
	private TaskExplorer taskExplorer;
	
	@Override
	public void onTaskStartup(TaskExecution taskExecution) {
		System.out.println("startup: " + taskExecution);
	}

	@Override
	public void onTaskEnd(TaskExecution taskExecution) {
		System.out.println("end: " + taskExecution);
	}
	
	@PostConstruct
	public void postConstruct(){
		Page<TaskExecution> task = taskExplorer.findTaskExecutionsByName("application", pageRequest);
		Assert.isTrue(!task.hasContent());
	}
	
	@PreDestroy
	public void preDestroy(){
		Page<TaskExecution> task = taskExplorer.findTaskExecutionsByName("application", pageRequest);
		Assert.isTrue(task.hasContent());
		TaskExecution taskExecution = task.getContent().get(0);
		Assert.isTrue(taskExecution.getEndTime()!=null);
		System.out.println("after: " + taskExecution);
		
	}

	@Override
	public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
	}
}