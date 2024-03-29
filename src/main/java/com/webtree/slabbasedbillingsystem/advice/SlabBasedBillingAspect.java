package com.webtree.slabbasedbillingsystem.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Aspect
@Log4j2
public class SlabBasedBillingAspect {

	
	@AfterReturning(value = "execution(* com.webtree.slabbasedbillingsystem.service.SlabBasedBillingServiceImpl.*(..))", returning = "result")
	public void afterReturningServiceMethodExecution(JoinPoint joinPoint,Object result) {
		String methodName = joinPoint.getSignature().getName();
		log.info("Method invoked: "+methodName);
		log.info("After executing " +methodName+" method. Result: " + result);
	}

}
