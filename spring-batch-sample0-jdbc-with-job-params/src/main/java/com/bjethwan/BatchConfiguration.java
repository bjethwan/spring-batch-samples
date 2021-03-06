package com.bjethwan;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

	@Autowired private JobBuilderFactory jobBuilderFactory;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	@StepScope
	public Tasklet helloWorldtasklet(@Value("#{jobParameters[msg]}") String msg){
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println(">>> "+ msg);
				return RepeatStatus.FINISHED;
			}
		};
	}
	
	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1")
				.tasklet(helloWorldtasklet(null)).build();
	}
	
	@Bean
	public Job helloWorldJob(){
		return jobBuilderFactory.get("helloWorldJob")
				.start(step1())
				.build();
	}
}
