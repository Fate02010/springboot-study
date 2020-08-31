package com.fate.srping.mqtt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {


    /**
     * mqtt 发送者的实现
     */
    @Autowired
    private IMqttSender iMqttSender;

    /**
     * 发送信息
     * 
     * @param topic
     *            主题
     * @param data
     *            数据
     */
    public void sendMessage(String topic, int qps, String data) {
        iMqttSender.sendToMqtt(topic, qps, data);
    }

}
