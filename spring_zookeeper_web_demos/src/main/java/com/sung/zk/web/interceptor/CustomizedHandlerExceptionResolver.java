package com.sung.zk.web.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CustomizedHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {
   private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedHandlerExceptionResolver.class);

   public int getOrder() {
      return Integer.MIN_VALUE;
   }

   public ModelAndView resolveException(HttpServletRequest aReq, HttpServletResponse aRes, Object aHandler,
         Exception exception) {
      if (aHandler instanceof HandlerMethod) {
         if (exception instanceof BindException) {
            return null;
         }
      }
      LOGGER.error(StringUtils.EMPTY, exception);
      ModelAndView mav = new ModelAndView("common/error");
      String errorMsg = exception.getMessage();
      aRes.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      if ("XMLHttpRequest".equals(aReq.getHeader("X-Requested-With"))) {
         try {
            aRes.setContentType("application/text; charset=utf-8");
            PrintWriter writer = aRes.getWriter();
            aRes.setStatus(HttpServletResponse.SC_FORBIDDEN);
            writer.print(errorMsg);
            writer.flush();
            writer.close();
            return null;
         } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
         }
      }
      mav.addObject("errorMsg", errorMsg);
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw, true);
      exception.printStackTrace(pw);
      mav.addObject("stackTrace", sw.getBuffer().toString());
      mav.addObject("exception", exception);
      return mav;
   }

}