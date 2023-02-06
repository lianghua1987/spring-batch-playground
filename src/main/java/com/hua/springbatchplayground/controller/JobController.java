package com.hua.springbatchplayground.controller;

import com.hua.springbatchplayground.service.Job2Service;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    Job2Service job2Service;

    @Autowired
    JobOperator jobOperator;

    @GetMapping("/start/{jobName}")
    public String startJob(@PathVariable String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        job2Service.startJob2(jobName);
        return "Job started";
    }

    @GetMapping("/stop/{executionId}")
    public String stopJob(@PathVariable long executionId) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        jobOperator.stop(executionId); // Complete the particular chunk and then stop
        return "Job stopped";
    }
}
