package com.bjethwan;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1")
				.tasklet( new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println(">>> This is step-1");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step evenStep(){
		return stepBuilderFactory.get("evenStep")
				.tasklet( new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println(">>> This is the even step !!!");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public Step oddStep(){
		return stepBuilderFactory.get("oddStep")
				.tasklet( new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
						System.out.println(">>> This is the odd step !!!");
						return RepeatStatus.FINISHED;
					}
				}).build();
	}
	
	@Bean
	public JobExecutionDecider decider(){
		return new OddEvenDecider();
	}
	
	
	
	@Bean
	public Job helloWorldJob(){
		return jobBuilderFactory.get("helloWorldJob")
				.start(step1())
				.next(decider())
				.from(decider()).on("ODD").to(oddStep())
				.from(decider()).on("EVEN").to(evenStep())
				.from(oddStep()).on("*").to(evenStep())
			
				.end()
				.build();
	}
}
