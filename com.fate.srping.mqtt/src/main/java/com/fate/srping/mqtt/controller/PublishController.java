package com.fate.srping.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fate.srping.mqtt.service.DynamicConsumerService;
import com.fate.srping.mqtt.service.MqttPublisherService;


@RestController
@RequestMapping("mqtt")
public class PublishController {

    @Autowired
    private MqttPublisherService mqttPublish;

    @Autowired
    private DynamicConsumerService consumerService;

    /**
     * 发送指定主题
     * 
     * @param topic
     *            主题
     * @param data
     *            数据
     * @return
     */
    @RequestMapping(value = "/publishTopic/{topic}/{data}", method = RequestMethod.GET)
    public String publicTopicData(@PathVariable("topic") String topic, @PathVariable("data") String data) {
        mqttPublish.sendMessage(topic, 1, data);
        return "ok";
    }
    
    /**
     * 创建消费者
     * 
     * @return
     */
    @RequestMapping(value = "/consumer/create", method = RequestMethod.GET)
    public String createConsumer() {
        return consumerService.createConsumer();
    }
    
    @RequestMapping(value = "/consumer/remove/{id}", method = RequestMethod.GET)
    public void removeConsumer(@PathVariable("id") String id) {
        consumerService.disConnect(id);
    }

}
