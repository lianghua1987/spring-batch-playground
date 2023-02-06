package com.hua.springbatchplayground.config;

import com.hua.springbatchplayground.processor.ItemProcessor2;
import com.hua.springbatchplayground.reader.ItemReader2;
import com.hua.springbatchplayground.writer.ItemWriter2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJob2 {

    @Autowired
    ItemReader2 itemReader;
    @Autowired
    ItemProcessor2 itemProcessor;
    @Autowired
    ItemWriter2 itemWriter;

    @Bean
    public Job job2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("Hua job2", jobRepository)
                .incrementer(new RunIdIncrementer()) // work with program args
                .start(step(jobRepository, transactionManager))
                .build();
    }

    private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Job2 Step started ")
                .<Integer, Integer>chunk(3)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

}
