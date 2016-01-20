package com.sung.spring.cache.convert;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;

/**
 * Created by sungang on 2016/1/4.17:34
 */
public class CacheAspect {
    private boolean enableCache = true;

    public boolean isEnableCache() {
        return this.enableCache;
    }

    public void setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
    }

    public Object cache(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        if (!this.enableCache) {
            return method.invoke(pjp.getTarget(), pjp.getArgs());
        }

        Object obj = pjp.proceed();
        Cacheable convert = (Cacheable) method.getAnnotation(Cacheable.class);
        if ((convert != null) && (!method.getReturnType().isInstance(obj))) {
            return JsonsmartSerializationRedisSerializer.gson.fromJson((String) obj, method.getReturnType());
        }

        return obj;
    }
}
