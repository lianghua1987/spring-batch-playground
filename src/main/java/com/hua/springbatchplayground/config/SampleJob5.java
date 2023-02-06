package com.hua.springbatchplayground.config;

import com.hua.springbatchplayground.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SampleJob5 {

    @Bean
    public Job job5(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("Hua job5", jobRepository)
                .incrementer(new RunIdIncrementer()) // work with program args
                .start(step(jobRepository, transactionManager))
                .build();
    }

    private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Job5 Step started ")
                .<Student, Student>chunk(3)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }


    private StaxEventItemReader<Student> reader() {
        StaxEventItemReader<Student> reader = new StaxEventItemReader<>();
        reader.setResource(new PathMatchingResourcePatternResolver().getResource("input/students.xml"));
        reader.setFragmentRootElementName("student");
        reader.setUnmarshaller(new Jaxb2Marshaller() {{
            setClassesToBeBound(Student.class);
        }});
        return reader;
    }


    private StaxEventItemWriter<Student> writer() {
        StaxEventItemWriter<Student> writer = new StaxEventItemWriter<>();
        writer.setResource(new FileSystemResource("output/students.xml"));
        writer.setRootTagName("students");
        writer.setMarshaller(new Jaxb2Marshaller() {{
            setClassesToBeBound(Student.class);
        }});
        return writer;
    }
}
