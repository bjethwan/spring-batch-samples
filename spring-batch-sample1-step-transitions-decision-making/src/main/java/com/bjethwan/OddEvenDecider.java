package com.bjethwan;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;


/*
 * The decider has to be repeatable, as its result is not stored in job repository.
 */
public class OddEvenDecider implements JobExecutionDecider {
	
	private int count = 0;

	@Override
	public FlowExecutionStatus decide(JobExecution arg0, StepExecution arg1) {
		count++;
		
		if(count%2 == 0)
			return new FlowExecutionStatus("EVEN");
		else
			return new FlowExecutionStatus("ODD");
			
	}

}
