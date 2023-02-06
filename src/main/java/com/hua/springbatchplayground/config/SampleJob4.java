package com.hua.springbatchplayground.config;

import com.hua.springbatchplayground.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Instant;

@Configuration
public class SampleJob4 {

    @Bean
    public Job job4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("Hua job4", jobRepository)
                .incrementer(new RunIdIncrementer()) // work with program args
                .start(step(jobRepository, transactionManager))
                .build();
    }

    private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Job4 Step started ")
                .repository(jobRepository)
                .<Student, Student>chunk(3)
                .transactionManager(transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }


    private JsonItemReader<Student> reader() {
        System.out.println("reader");
        JsonItemReader<Student> reader = new JsonItemReader<>();
        reader.setResource(new PathMatchingResourcePatternResolver().getResource("input/students.json"));
        reader.setJsonObjectReader(new JacksonJsonObjectReader<>(Student.class));
        reader.setMaxItemCount(8);
        reader.setCurrentItemCount(2);
        return reader;
    }

    private JsonFileItemWriter<Student> writer() {
        System.out.println("writer");
        JsonFileItemWriter<Student> writer = new JsonFileItemWriter<>(
                new FileSystemResource("output/students.json"),
                new JacksonJsonObjectMarshaller<>());
        return writer;
    }

}
