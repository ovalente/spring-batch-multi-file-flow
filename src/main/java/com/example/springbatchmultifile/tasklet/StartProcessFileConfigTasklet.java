package com.example.springbatchmultifile.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.example.springbatchmultifile.configuration.ClientFileConfiguration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartProcessFileConfigTasklet implements Tasklet {

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        return RepeatStatus.FINISHED;
    }
    
}
