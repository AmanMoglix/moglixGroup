package com.hackathon.oms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oms")
@CrossOrigin
public class OmsController {
private static Logger logger=LoggerFactory.getLogger(OmsController.class);
@RequestMapping(value="/get",method= {RequestMethod.GET,RequestMethod.POST})
public String get() {
	logger.info("Oms controller hitttinh");
	return "Hi oms";
}
}
