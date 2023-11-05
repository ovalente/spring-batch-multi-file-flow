package com.example.springbatchmultifile.decider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.lang.Nullable;

import com.example.springbatchmultifile.configuration.ClientFileConfiguration;
import static com.example.springbatchmultifile.utils.BatchConstant.NEXT_FILE_CONFIG;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcessNextFileConfig implements JobExecutionDecider {

    private final ClientFileConfiguration clientFileConfiguration;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, @Nullable StepExecution stepExecution) {
        if (this.clientFileConfiguration
                .getConfigProcessCount() >= this.clientFileConfiguration.getFileConfigurations().size() - 1) {
            return FlowExecutionStatus.COMPLETED;
        }
        this.clientFileConfiguration.setConfigProcessCount(this.clientFileConfiguration.getConfigProcessCount() + 1);
        return new FlowExecutionStatus(NEXT_FILE_CONFIG);
    }
    
}
