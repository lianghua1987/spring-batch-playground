- Spring Batch 5.0 Goes GA! - https://spring.io/blog/2022/11/24/spring-batch-5-0-goes-ga
- Spring Batch 5.0 Migration Guide - https://github.com/spring-projects/spring-batch/wiki/Spring-Batch-5.0-Migration-Guide

### Sample
```java
// Sample with v4
@Configuration
@EnableBatchProcessing
public class MyJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public Job myJob(Step step) {
        return this.jobBuilderFactory.get("myJob")
                .start(step)
                .build();
    }

}
```
```java
// Sample with v5
@Configuration
@EnableBatchProcessing
public class MyJobConfig {

    @Bean
    public Job myJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("myJob", jobRepository)
                .start(step)
                .build();
    }

}
```

## Troubleshoot
> With Spring Boot 3, there is no need for @EnableBatchProcessing. If you add it, the auto-configuration of Spring Batch (including the automatic launching of jobs at startup) will back off.