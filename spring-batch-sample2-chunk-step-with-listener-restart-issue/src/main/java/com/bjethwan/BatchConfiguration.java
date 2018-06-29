package com.bjethwan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BatchConfiguration {

	@Autowired private JobBuilderFactory jobBuilderFactory;
	@Autowired private StepBuilderFactory stepBuilderFactory;

	@Bean
	public ItemReader<String> reader(){
		ArrayList<String> list = new ArrayList<>();
		for(int i=1;i<=100;i++)
			list.add(Integer.toString(i));
		return new MyItemReader(list);
	}


	@Bean
	public ItemWriter<String> writer(){
		return new ItemWriter<String>() {
			@Override
			public void write(List<? extends String> items) throws Exception {
				for(String item: items)	System.out.println(">>> Processing "+ item);
			}
		};
	}

	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1")
				.<String, String>chunk(10)
				.faultTolerant()
				.listener(new MyChunkListener())
				.reader(reader())
				.writer(writer())
				.build();
	}

	@Bean
	public Job helloWorldJob(){
		return jobBuilderFactory.get("helloWorldJob").start(step1()).build();
	}
}
