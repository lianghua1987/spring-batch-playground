package com.hua.springbatchplayground.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Job2Service {
    @Autowired
    JobLauncher jobLauncher;
    @Qualifier("job1")
    @Autowired
    Job job1;
    @Qualifier("job2")
    @Autowired
    Job job2;
    @Async
    public void startJob2(String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis(), Long.class));
        JobParameters jobParameters = new JobParameters();
        JobExecution jobExecution = jobLauncher.run(jobName.equals("job1") ? job1 : job2, jobParameters);
        System.out.println(jobExecution.getId());
    }
}
