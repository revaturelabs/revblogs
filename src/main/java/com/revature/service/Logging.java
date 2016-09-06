package com.revature.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/*
 * Logger for debugging
 */

@Aspect
public class Logging {
	
	private static Logger log = Logger.getRootLogger(); 
/**
 * Temporarily moved to non-smell
//	@Around("logPerformance()")
//	public Object intercept(ProceedingJoinPoint pjp){
//		//Object to eventually be sent temporarily null
//		Object result = null;
//		
//		try {
//			//Send to the logger a debug message that the method is being executed
//			log.debug("Executed Method (" + pjp.getSignature()+ ")!" );
//			
//			//Execute the intercepted method
//			result = pjp.proceed(); 
//			
//			//Send to the logger an info message that the method succeeded
//			log.info("Returned: " + result);
//		} catch (Throwable e) {
//			//Send to the logger an error message that the method failed
//			log.error(e);
//		}
//		//Send null or the actual result back to the chain
//		return result;
//	}
	*/
	//Temporary Logging functions for Bugs on SonarQube
	//Remove if necessary
	public static void info(Throwable t){log.info(t);}
	
	 //------------------POINTCUTS------------------//
	//Whenever any method is executed
	@Pointcut("execution(* *(..))")
	public void logPerformance(){
		//Empty for readability of above code
	}
		
	@Around("pointcutHook()")
	public Object intercept(ProceedingJoinPoint pjp){
		
		Object result = null;
		
		try {
			
			//BEFORE EXECUTION
			log.debug("[AOP LOGGER]: Enter Method (" + pjp.getSignature()+ ") Execution" );
			
			//Execute the intercepted method
			result = pjp.proceed(); 
			
			//AFTER EXECUTION
			log.debug("[AOP LOGGER]: Exit Method (" + pjp.getSignature()+ ") Execution" );
			
			if(result != null){
				
				log.info("[AOP LOGGER]: (" + pjp.getSignature() + ") returned: " + result);
			}
			
		} catch (Throwable e) {
			
			//Send to the logger an error message that the method failed
			log.error("[AOP LOGGER]:" + e);
		}
		
		return result;
	}
	
	public void log(String message){
		
		log.info("[AOP LOGGER]:" + message);
	}
	
	@Pointcut("within(com.revature.*)")
	private void pointcutHook() {}
}
