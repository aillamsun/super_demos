package com.sung.zk.web.controller;

import com.sung.zk.util.ConfUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("")
public class IndexController {

   @RequestMapping(value = { "", "/", "/index" })
   public ModelAndView index() {
      ModelAndView mav = new ModelAndView("index");
      mav.addObject("addrs", ConfUtils.getConxtions());
      return mav;
   }

}
