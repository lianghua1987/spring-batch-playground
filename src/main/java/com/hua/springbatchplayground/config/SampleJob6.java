//package com.hua.springbatchplayground.config;
//
//import com.hua.springbatchplayground.model.Student;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.ItemPreparedStatementSetter;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
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
//                .writer(writer())
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
//        return reader;
//    }
//
//
//    private JdbcBatchItemWriter<Student> writer() {
//        JdbcBatchItemWriter<Student> writer = new JdbcBatchItemWriter<>();
//        writer.setDataSource(customDataSource());
//        writer.setSql("insert into student(id, firstName, lastName, email) values(:id, :firstName, :lastName, :Email)");
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        return writer;
//    }
//
//
//
//    private JdbcBatchItemWriter<Student> writerWithPrepStmt() {
//        JdbcBatchItemWriter<Student> writer = new JdbcBatchItemWriter<>();
//        writer.setDataSource(customDataSource());
//        writer.setSql("insert into student(id, firstName, lastName, email) values(?,?,?,?)");
//        writer.setItemPreparedStatementSetter((student, preparedStatement) -> {
//            preparedStatement.setLong(1, student.getId());
//            preparedStatement.setString(2, student.getFirstName());
//            preparedStatement.setString(3, student.getLastName());
//            preparedStatement.setString(4, student.getEmail());
//        });
//        return writer;
//    }
//}
