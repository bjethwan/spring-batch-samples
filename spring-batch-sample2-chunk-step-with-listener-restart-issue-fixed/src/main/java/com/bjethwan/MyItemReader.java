package com.bjethwan;

import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class MyItemReader implements ItemStreamReader<String>{
	
	private int curIndex;
	private boolean restart;
	private final List<String> items;
	
	public MyItemReader(List<String> items){
		this.items = items;
		this.curIndex = 0;
		this.restart = false;
	}
	
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		String item = null;
		if(curIndex < this.items.size()){
			item = this.items.get(curIndex);
			curIndex++;
		}
		if(curIndex==42 && !restart){
			throw new RuntimeException("This could be any issue during read/process");
		}
		return item;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		if(executionContext.containsKey("curIndex")){
			this.restart=true;
			this.curIndex = executionContext.getInt("curIndex");
		}else{
			this.curIndex = 0;
			executionContext.put("curIndex", this.curIndex);
		}
		
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		executionContext.put("curIndex", this.curIndex);
	}
	
	@Override
	public void close() throws ItemStreamException {
		
		
	}
}
