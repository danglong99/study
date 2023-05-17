package com.example.spring.other;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AspectService {

  private static final Logger logger = LoggerFactory.getLogger(AspectService.class);
  @Around("@annotation(CustomAnnotation)")
  public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    logger.info("Getting record {}", proceedingJoinPoint.getArgs());
    return proceedingJoinPoint.proceed();
  }
}
