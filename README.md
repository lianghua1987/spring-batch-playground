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
```bash
docker run --name mysql -e MySQL_ROOT_PASSWPRD=admin -p 3306:3306 mysql:8
docker exec -it mysql  /bin/bash
mysql -uroot -p

mysql> select * from BATCH_JOB_EXECUTION;
+------------------+---------+-----------------+----------------------------+----------------------------+----------------------------+-----------+-----------+--------------+----------------------------+
| JOB_EXECUTION_ID | VERSION | JOB_INSTANCE_ID | CREATE_TIME                | START_TIME                 | END_TIME                   | STATUS    | EXIT_CODE | EXIT_MESSAGE | LAST_UPDATED               |
+------------------+---------+-----------------+----------------------------+----------------------------+----------------------------+-----------+-----------+--------------+----------------------------+
|                1 |       2 |               1 | 2023-02-05 20:30:12.097013 | 2023-02-05 20:30:12.131553 | 2023-02-05 20:30:12.438864 | COMPLETED | COMPLETED |              | 2023-02-05 20:30:12.439919 |
+------------------+---------+-----------------+----------------------------+----------------------------+----------------------------+-----------+-----------+--------------+----------------------------+

```
## Troubleshoot
- With Spring Boot 3, there is no need for @EnableBatchProcessing. If you add it, the auto-configuration of Spring Batch (including the automatic launching of jobs at startup) will back off.
- Step already complete or not restartable, so no action to execute - ```Step.allowStartIfComplete(true)```