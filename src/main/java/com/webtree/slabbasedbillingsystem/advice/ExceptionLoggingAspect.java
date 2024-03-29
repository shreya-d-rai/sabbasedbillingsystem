package com.webtree.slabbasedbillingsystem.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Aspect
@Log4j2
public class ExceptionLoggingAspect {
	
	@Before(value = "@annotation(com.webtree.slabbasedbillingsystem.annotation.ExceptionInfo) && args(exception)")
	public void expectionAdvice(JoinPoint joinPoint, Exception exception) {
		log.error(exception.getMessage(),exception);	
	}

}
