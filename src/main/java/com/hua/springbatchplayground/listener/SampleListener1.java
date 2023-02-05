package com.hua.springbatchplayground.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class SampleListener1 implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("SampleListener1 before job:");
        System.out.println("Job name: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job params: " + jobExecution.getJobParameters());
        System.out.println("Job execution context: " + jobExecution.getExecutionContext());
        JobExecutionListener.super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("SampleListener1 after job:");
        System.out.println("Job name: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job params: " + jobExecution.getJobParameters());
        System.out.println("Job execution context: " + jobExecution.getExecutionContext());
        JobExecutionListener.super.afterJob(jobExecution);
    }
}
