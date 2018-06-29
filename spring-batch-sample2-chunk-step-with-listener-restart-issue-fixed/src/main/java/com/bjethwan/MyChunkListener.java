package com.bjethwan;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;

public class MyChunkListener {
	
	@BeforeChunk
	public void in(){
		System.out.println("******************Chunk Begin***************************");
	}
	
	@AfterChunk
	public void out(){
		System.out.println("******************Chunk Complete************************");
	}

}
