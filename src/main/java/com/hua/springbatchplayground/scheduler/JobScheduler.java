package com.hua.springbatchplayground.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JobScheduler {
    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("job2")
    @Autowired
    Job job2;

    @Qualifier("job3")
    @Autowired
    Job job3;

    @Qualifier("job4")
    @Autowired
    Job job4;

    @Qualifier("job5")
    @Autowired
    Job job5;


//    @Qualifier("job6")
//    @Autowired
//    Job job6;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void scheduleJob() {
//        schedule(job2);
        schedule(job3);
//        schedule(job4);
//        schedule(job5);
    }

    private void schedule(Job job) {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis(), Long.class));
        JobParameters jobParameters = new JobParameters();
        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            System.out.println(jobExecution.getId());
        } catch (Exception e) {
            System.out.println("Exception while start " + job.getName() + ", " + e.getMessage());
        }
    }
}
