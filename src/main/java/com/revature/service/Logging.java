package com.revature.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/*
 * Logger for debugging
 */

@Aspect
public class Logging {
	
	private static Logger log = Logger.getRootLogger(); 

//  Temporarily moved to non-smell
	@Before("logPerformance()")
	public void logInsert(JoinPoint jp){
		log.debug("[REVBLOGS LOGGER]: Executing Method " + jp.getSignature());
	}
	@AfterReturning(pointcut="logPerformance()", returning="obj")
	public void logAfterReturning(JoinPoint jp, Object obj){
					// obj is whatever the intercepted method has returned
		log.info("[REVBLOGS LOGGER]: "+jp.getSignature()+" Returned: " + obj);
	}
	@AfterThrowing(pointcut="logPerformance()", throwing="e")
	public void logGetException(JoinPoint jp, Exception e){
		log.error("[REVBLOGS LOGGER]: "+jp.getSignature()+" threw "+e);
	}
	
	public void log(String message){		
		log.info("[AOP LOGGER]:" + message);		
	}
	public static void info(Throwable t){log.info(t);}

//	
//	 //------------------POINTCUTS------------------//
//	//Whenever any method is executed
	@Pointcut("within(com.revature..*..*)")
	public void logPerformance(){
		//Empty for readability of above code
	}
}
