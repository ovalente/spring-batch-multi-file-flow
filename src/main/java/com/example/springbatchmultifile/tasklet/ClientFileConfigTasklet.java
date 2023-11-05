package com.example.springbatchmultifile.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;

import com.example.springbatchmultifile.configuration.ClientFileConfiguration;
import com.example.springbatchmultifile.configuration.FileConfiguration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientFileConfigTasklet implements Tasklet {

    private final ClientFileConfiguration clientFileConfiguration;

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<FileConfiguration> configurations = new ArrayList<>();      
        configurations.add(buildConfig("simplePerson.csv"));
        configurations.add(buildConfig("complexPerson.csv"));
        //Pour faire la premier configuration en premier
        clientFileConfiguration.setConfigProcessCount(-1);
        clientFileConfiguration.setFileConfigurations(configurations);
        return RepeatStatus.FINISHED;
    }
 
    private FileConfiguration buildConfig(String filename) {
        FileConfiguration config = new FileConfiguration();
        config.setFilename(filename);
        return config;
    }
    
}
