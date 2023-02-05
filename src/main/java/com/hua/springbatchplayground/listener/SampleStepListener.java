package com.hua.springbatchplayground.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class SampleStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("SampleStepListener before job:");
        System.out.println("Step name: " + stepExecution.getStepName());
        System.out.println("Job execution context: " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Step execution context: " + stepExecution.getExecutionContext());
        StepExecutionListener.super.beforeStep(stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("SampleStepListener after job:");
        System.out.println("Step name: " + stepExecution.getStepName());
        System.out.println("Job execution context: " + stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Step execution context: " + stepExecution.getExecutionContext());
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
