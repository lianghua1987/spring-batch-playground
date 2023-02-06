//package com.hua.springbatchplayground.config;
//
//import com.hua.springbatchplayground.model.Student;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//public class SampleJob6 {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.customdatasource")
//    public DataSource customDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public Job job6(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new JobBuilder("Hua job6", jobRepository)
//                .incrementer(new RunIdIncrementer()) // work with program args
//                .start(step(jobRepository, transactionManager))
//                .build();
//    }
//
//    private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("Job6 Step started ")
//                .<Student, Student>chunk(3)
//                .repository(jobRepository)
//                .transactionManager(transactionManager)
//                .reader(reader())
//                .writer(chunk -> chunk.forEach(System.out::println))
//                .build();
//    }
//
//    private JdbcCursorItemReader<Student> reader() {
//        JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<>();
//        reader.setDataSource(customDataSource());
//        reader.setSql("select id, firstName, lastName, email from student");
//        reader.setRowMapper(new BeanPropertyRowMapper<>() {{
//            setMappedClass(Student.class);
//        }});
//
//        return reader;
//    }
//}
