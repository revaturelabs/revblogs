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
	
	// Auto Logs
	
	@Before("logPerformance()")
	public void logInsert(JoinPoint jp){
		log.debug("[REVBLOGS LOGGER]: Executing Method " + jp.getSignature());
	}
	@AfterReturning(pointcut="logPerformance()", returning="obj")
	public void logAfterReturning(JoinPoint jp, Object obj){
					// obj is whatever the intercepted method has returned
		log.debug("[REVBLOGS LOGGER]: "+jp.getSignature()+" Returned: " + obj);
	}
	@AfterThrowing(pointcut="logPerformance()", throwing="e")
	public void logGetException(JoinPoint jp, Exception e){
		log.error("[REVBLOGS LOGGER]: "+jp.getSignature()+" threw "+e);
	}
	
	// Manual Logs (Static to prevent class instantiation)
	
	public static void info(String str){
		log.info("[REVBLOGS LOGGER]:" + str);
	}
	public static void error (Exception e){
		log.error("[REVBLOGS LOGGER]:" + e);
	}

	//------------------POINTCUTS------------------//

	@Pointcut("within(com.revature..*..*)")
	public void logPerformance(){
		//Empty for aop, STOP deleting this comment
	}
}
