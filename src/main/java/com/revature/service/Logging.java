package com.revature.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Logger for debugging
 */

@Aspect
public class Logging {
	
	private static Logger log = Logger.getRootLogger(); 
	
	@Around("logPerformance()")
	public Object intercept(ProceedingJoinPoint pjp){
		//Object to eventually be sent temporarily null
		Object result = null;
		
		try {
			//Send to the logger a debug message that the method is being executed
			log.debug("Executed Method (" + pjp.getSignature()+ ")!" );
			
			//Execute the intercepted method
			result = pjp.proceed(); 
			
			//Send to the logger an info message that the method succeeded
			log.info("Returned: " + result);
		} catch (Throwable e) {
			//Send to the logger an error message that the method failed
			log.error(e);
		}
		//Send null or the actual result back to the chain
		return result;
	}
	
	//Temporary Logging functions for Bugs on SonarQube
	//Remove if necessary
	public void info(Throwable t){log.info(t);}
	//For Code smells
	public void log(String str){log.info(str);}
	
	 //------------------POINTCUTS------------------//
	//Whenever any method is executed
	@Pointcut("execution(* *(..))")
	public void logPerformance(){}
}
