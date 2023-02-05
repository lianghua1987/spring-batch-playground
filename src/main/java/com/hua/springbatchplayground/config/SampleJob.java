package com.hua.springbatchplayground.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJob {

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        System.out.println("job");
        return new JobBuilder("Hua job", jobRepository)
                .start(step(jobRepository, transactionManager, 1))
                .next(step(jobRepository, transactionManager, 2))
                .next(step(jobRepository, transactionManager, 3))
                .next(step(jobRepository, transactionManager, 4))
                .build();
    }

    private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager, int step) {
        System.out.println("step");
        return new StepBuilder("Hua 1st step", jobRepository)
                .tasklet(tasklet(step), transactionManager)
                .build();
    }

    private Tasklet tasklet(int step) {
        return (contribution, chunkContext) -> {
            System.out.println("tasklet " + step);
            return RepeatStatus.FINISHED;
        };
    }
}
