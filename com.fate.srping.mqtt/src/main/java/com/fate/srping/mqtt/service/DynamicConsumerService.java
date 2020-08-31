package com.fate.srping.mqtt.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fate.srping.mqtt.config.MqttConfig;

/**
 * 动态创建消费者
 * 
 * @author maijinchao
 *
 * @date 2020/08/25
 */
@Service
public class DynamicConsumerService {

    @Autowired
    private MqttConfig mqttConfig;

    /**
     * mqtt 的客户端
     */
    private volatile static MqttClient mqttClient;

    /**
     * 负责记住连接上的客户端
     */
    private static Map<String, MqttClient> clientMap = new ConcurrentHashMap<String, MqttClient>();


    /**
     * 动态创建消费者,返回消费者 ID
     * 
     * @return 返回消费者 ID
     */
    public String createConsumer() {
        String clientid = UUID.randomUUID().toString();
        try {
            mqttClient = new MqttClient("tcp://127.0.0.1:1883", clientid, new MemoryPersistence());
            mqttClient.connect(getOptions());
            mqttClient.subscribe("test123", 1);
            clientMap.put(clientid, mqttClient);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    // TODO Auto-generated method stub
                    System.out.println("connectionLost");
                    
                }
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // TODO Auto-generated method stub
                    System.out.println("Topic: " + topic + " Message: " + message.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // TODO Auto-generated method stub
                    
                }
                
            });
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return clientid;
    }

    /**
     * 通过 ID 断开连接,通过执行 disconnect 是不会触发遗嘱消息,要异常关闭才会触发
     * 
     * @param id
     * @return
     */
    public void disConnect(String id) {
        try {
            MqttClient client = DynamicConsumerService.clientMap.get(id);
            if (client != null) {
                client.disconnect();
                DynamicConsumerService.clientMap.remove(id);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取 mqtt 的连接参数
     * 
     * @return
     */
    private MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName("admin");
        options.setPassword("admin".toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        // 设置客户端的遗嘱信息
        options.setWill(mqttConfig.getWillTopic(), "offline".getBytes(), 1, true);
        return options;

    }
}
