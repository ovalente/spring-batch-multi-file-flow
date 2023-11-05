package com.example.springbatchmultifile.decider;

import static com.example.springbatchmultifile.utils.BatchConstant.FILE_COMPLEX_CONFIG;
import static com.example.springbatchmultifile.utils.BatchConstant.FILE_SIMPLE_CONFIG;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.lang.Nullable;

import com.example.springbatchmultifile.configuration.ClientFileConfiguration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcesserFileDecider implements JobExecutionDecider {
    private final ClientFileConfiguration clientFileConfiguration;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, @Nullable StepExecution stepExecution) {
        String filename = this.clientFileConfiguration.getCurrentConfig().getFilename();
        jobExecution.getExecutionContext().put("filename", filename);
        
        if (StringUtils.containsIgnoreCase(filename, "simple")){
            return new FlowExecutionStatus(FILE_SIMPLE_CONFIG);
        } else {
            return new FlowExecutionStatus(FILE_COMPLEX_CONFIG);
        }
    }
}
