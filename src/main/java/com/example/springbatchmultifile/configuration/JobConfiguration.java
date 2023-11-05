package com.example.springbatchmultifile.configuration;

import static com.example.springbatchmultifile.utils.BatchConstant.FILE_COMPLEX_CONFIG;
import static com.example.springbatchmultifile.utils.BatchConstant.FILE_SIMPLE_CONFIG;
import static com.example.springbatchmultifile.utils.BatchConstant.NEXT_FILE_CONFIG;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.springbatchmultifile.decider.ProcessNextFileConfig;
import com.example.springbatchmultifile.decider.ProcesserFileDecider;
import com.example.springbatchmultifile.entity.PersonEntity;
import com.example.springbatchmultifile.model.PersonComplexDTO;
import com.example.springbatchmultifile.model.PersonSimpleDTO;
import com.example.springbatchmultifile.tasklet.ClientFileConfigTasklet;
import com.example.springbatchmultifile.tasklet.StartProcessFileConfigTasklet;

@Configuration
public class JobConfiguration {

    @Bean
    public Job jobExtraction(JobRepository jobRepository,
                             PlatformTransactionManager platformTransactionManager,
                             JobExecutionDecider processNextFileConfig,
                             JobExecutionDecider processerFileDecider,
                             Step jobConfigStep,
                             Step jobStartProcessFileStep,
                             Step traitementFichierSimple,
                             Step traitementFichierComplexe) {
                                
        return new JobBuilder("jobExtraction", jobRepository)
                .start(jobConfigStep)
                .next(jobStartProcessFileStep)
                .next(processNextFileConfig).on(NEXT_FILE_CONFIG)
                .to(processerFileDecider).on(FILE_SIMPLE_CONFIG).to(traitementFichierSimple).next(jobStartProcessFileStep)
                .from(processerFileDecider).on(FILE_COMPLEX_CONFIG).to(traitementFichierComplexe).next(jobStartProcessFileStep)
                .from(processNextFileConfig).on(FlowExecutionStatus.COMPLETED.getName()).end()
                .build()
                .build(); 
    }

    @Bean
    @JobScope
    public ClientFileConfiguration clientFileConfiguration() {
        return new ClientFileConfiguration();
    }

    @Bean
    public Step jobConfigStep(JobRepository jobRepository, 
                              PlatformTransactionManager platformTransactionManager,
                              Tasklet clientConfigTasklet) {
         return new StepBuilder("configStep", jobRepository)
                .tasklet(clientConfigTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Step jobStartProcessFileStep(JobRepository jobRepository, 
                              PlatformTransactionManager platformTransactionManager,
                              Tasklet startProcessFileConfigTasklet) {
         return new StepBuilder("startProcessFileStep", jobRepository)
                .tasklet(startProcessFileConfigTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public ProcessNextFileConfig processNextFileConfig(ClientFileConfiguration clientFileConfiguration) {
        return new ProcessNextFileConfig(clientFileConfiguration);
    }

    @Bean
    public ProcesserFileDecider processerFileDecider(ClientFileConfiguration clientFileConfiguration) {
        return new ProcesserFileDecider(clientFileConfiguration);
    }

    @Bean
    public Tasklet clientConfigTasklet(ClientFileConfiguration clientFileConfiguration) {
        return new ClientFileConfigTasklet(clientFileConfiguration);
    }

    @Bean
    public Tasklet startProcessFileConfigTasklet() {
        return new StartProcessFileConfigTasklet();
    }

    @Bean
    public Step traitementFichierSimple(JobRepository jobRepository, 
                                        PlatformTransactionManager platformTransactionManager, 
                                        ItemReader<PersonSimpleDTO> readerSimpleFile,
                                        ItemProcessor<PersonSimpleDTO, PersonEntity> simplePersonProcessor,
                                        ItemWriter<PersonEntity> personEntityWriter) {

        return new StepBuilder("traitementFichierSimple", jobRepository)
                    .<PersonSimpleDTO, PersonEntity>chunk(10, platformTransactionManager)
                    .reader(readerSimpleFile)
                    .processor(simplePersonProcessor)
                    .writer(personEntityWriter)
                    .build();
    }

    @Bean
    public Step traitementFichierComplexe(JobRepository jobRepository, 
                                        PlatformTransactionManager platformTransactionManager, 
                                        ItemReader<PersonComplexDTO> readerComplexFile,
                                        ItemProcessor<PersonComplexDTO, PersonEntity> complexPersonProcessorWithFilter,
                                        ItemWriter<PersonEntity> personEntityWriter) {

        return new StepBuilder("traitementFichierComplexe", jobRepository)
                    .<PersonComplexDTO, PersonEntity>chunk(10, platformTransactionManager)
                    .reader(readerComplexFile)
                    .processor(complexPersonProcessorWithFilter)
                    .writer(personEntityWriter)
                    .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PersonSimpleDTO> readerSimpleFile(@Value ("#{jobExecutionContext['filename']}") String filename) {
        return new FlatFileItemReaderBuilder<PersonSimpleDTO>()
                .name("personSimpleReader")
                .resource(new ClassPathResource(filename))
                .delimited()
                .names(new String[]{"firstname", "lastname", "age"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(PersonSimpleDTO.class);
                }})
                .linesToSkip(1)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PersonComplexDTO> readerComplexFile(@Value ("#{jobExecutionContext['filename']}") String filename) {
        return new FlatFileItemReaderBuilder<PersonComplexDTO>()
                .name("personComplexReader")
                .resource(new ClassPathResource(filename))
                .delimited()
                .names(new String[]{"firstname", "lastname", "title", "age"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(PersonComplexDTO.class);
                }})
                .linesToSkip(1)
                .build();
    }

    @Bean
    public ItemProcessor<PersonComplexDTO, PersonEntity> complexPersonProcessorWithFilter(ItemProcessor<PersonComplexDTO, PersonEntity> complexPersonProcessor, ItemProcessor<PersonEntity, PersonEntity> complexFilterPersonProcessor) {
        CompositeItemProcessor<PersonComplexDTO, PersonEntity> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(complexPersonProcessor, complexFilterPersonProcessor));
        return processor;
    }
}
