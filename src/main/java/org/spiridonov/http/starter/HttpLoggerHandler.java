package org.spiridonov.http.starter;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.spiridonov.http.starter.config.LoggerProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;


@Aspect
public class HttpLoggerHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpLoggerHandler.class);

    public HttpLoggerHandler(LoggerProperties loggerProperties) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logbackLogger = loggerContext.getLogger(HttpLoggerHandler.class);

        Level level = Level.toLevel(loggerProperties.getLevel().name(), Level.INFO);
        logbackLogger.setLevel(level);
    }

    @Around("@annotation(org.spiridonov.http.starter.annotations.HttpLoggable)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("HTTP REQUEST: {} {}", joinPoint.getSignature().getName(), joinPoint.getArgs());

        Object result;
        try {
            result = joinPoint.proceed();
            logger.debug("HTTP RESPONSE BODY: {}", result);
        } catch (Throwable ex) {
            logger.error("Exception in method {}: {}", joinPoint.getSignature(), ex.getMessage(), ex);
            throw ex;
        }
        return result;
    }

    @AfterReturning(pointcut = "@within(org.spiridonov.http.starter.annotations.HttpExceptionLoggable)",
    returning = "returnValue")
    public Object logAfterReturning(JoinPoint joinPoint, Object returnValue) {
        logger.debug("Exception handler called: {}", joinPoint.getSignature().getName());
        logger.error("Error value {}", returnValue);

        return returnValue;
    }
}
