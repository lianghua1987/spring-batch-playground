package com.hua.springbatchplayground.config;

import com.hua.springbatchplayground.listener.Job3SkipListener;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Instant;

@Configuration
public class SampleJob3 {

    @Autowired
    Job3SkipListener job3SkipListener;

    @Bean
    public Job job3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("Hua job3", jobRepository)
                .incrementer(new RunIdIncrementer()) // work with program args
                .start(step(jobRepository, transactionManager))
                .build();
    }

    private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Job3 Step started ")
                .<Student, Student>chunk(3)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .reader(reader(true))
                .processor(student -> {
                    System.out.println("processor");
                    return new Student(student.getId(), student.getFirstName() + "!", student.getLastName() + "!", student.getEmail() + "!");
                })
                .writer(writer())
                .faultTolerant()
                .skip(Throwable.class)
                .skipLimit(10) // .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .listener(job3SkipListener)
                .retryLimit(1)
                .retry(Throwable.class)
                .allowStartIfComplete(true)
                .build();
    }


    private FlatFileItemReader<Student> reader(boolean isValid) {
        System.out.println("reader");
        FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
        // using FileSystemResource if file stores in a directory instead of resource folder
        reader.setResource(new PathMatchingResourcePatternResolver().getResource(isValid ? "input/students.csv" : "input/students_invalid.csv"));
        reader.setLineMapper(new DefaultLineMapper<>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {{
                    setNames("ID", "First Name", "Last Name", "Email");
                }});

                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Student.class);
                }});
            }
        });
        reader.setLinesToSkip(1);
        return reader;
    }

    //@Bean
    public FlatFileItemWriter<Student> writer() {
        System.out.println("writer");
        FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output/students.csv"));
        writer.setHeaderCallback(writer1 -> writer1.write("Id,First Name,Last Name, Email"));
        writer.setLineAggregator(new DelimitedLineAggregator<>() {{
            setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                setNames(new String[]{"id", "firstName", "lastName", "email"});
            }});
        }});

        writer.setFooterCallback(writer12 -> writer12.write("Created @ " + Instant.now()));
        return writer;
    }


}
