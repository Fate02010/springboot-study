package com.fate.spring.kafkademo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KafkaController {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public String kafkaSend() {
        for (int i = 0; i < 12; i++) {
            kafkaTemplate.send("test", String.format("发送数据%s", i + ""));
        }
        return "OK";
	}
}