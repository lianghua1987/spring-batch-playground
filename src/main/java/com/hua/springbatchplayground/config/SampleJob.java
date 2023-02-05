package com.hua.springbatchplayground.config;

import com.hua.springbatchplayground.listener.SampleListener1;
import com.hua.springbatchplayground.listener.SampleListener2;
import com.hua.springbatchplayground.listener.SampleStepListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJob {

    @Autowired
    SampleListener1 listener1;

    @Autowired
    SampleListener2 listener2;

    @Autowired
    SampleStepListener stepListener;

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("Hua job", jobRepository)
                .incrementer(new RunIdIncrementer()) // work with program args
                .start(step(jobRepository, transactionManager, 1))
                .next(step(jobRepository, transactionManager, 2))
                .listener(listener1)
                .listener(listener2)
                .build();
    }

    private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager, int step) {
        return new StepBuilder("Hua Step: " + step, jobRepository)
                .tasklet(tasklet(step), transactionManager)
                .listener(stepListener)
                .build();
    }

    private Tasklet tasklet(int step) {
        return (contribution, chunkContext) -> {
            System.out.print("tasklet " + step + ": ");
            System.out.println(chunkContext.getStepContext().getJobExecutionContext());
            return RepeatStatus.FINISHED;
        };
    }
}
