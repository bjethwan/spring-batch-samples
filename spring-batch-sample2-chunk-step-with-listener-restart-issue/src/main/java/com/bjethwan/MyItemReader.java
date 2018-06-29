package com.bjethwan;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class MyItemReader implements ItemReader<String>{
	
	private int curIndex;
	private final List<String> items;
	
	public MyItemReader(List<String> items){
		this.items = items;
		this.curIndex = 0;
	}
	
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		String item = null;
		if(curIndex < this.items.size()){
			item = this.items.get(curIndex);
			curIndex++;
		}
		if(curIndex==42){
			throw new RuntimeException("This could be any issue during read/process");
		}
		return item;
	}
}
