package com.fate.srping.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@ConfigurationProperties(prefix = "mqtt", ignoreUnknownFields = true)
public class MqttConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttConfig.class);

    /**
     * 订阅的bean名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";
    /**
     * 发布的bean名称
     */
    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";

    private static final byte[] WILL_DATA;

    static {
        WILL_DATA = "offline".getBytes();
    }

    /**
     * mqtt 连接队列 的用户名
     */
    private String username = "admin";

    /**
     * mqtt 连接队列的密码
     */
    private String password = "admin";

    /**
     * 推送信息的连接地址,因为是 demo 项目,所以设定为单 broker
     */
    private String url = "tcp://localhost:1883";

    /**
     * 连接服务器默认客户端ID
     */
    private String producerClientId = "producer123456";

    /**
     * 默认的推送主题，实际可在调用接口时指定
     */
    private String producerDefaultTopic = "test123";

    /**
     * 连接服务器默认客户端ID (消费者)
     */
    private String consumerClientId = "consumer123456";

    /**
     * 默认的接收主题.
     */
    private String consumerDefaultTopic = "test123";

    /**
     * 遗嘱主题
     */
    private String willTopic = "willTopic";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProducerClientId() {
        return producerClientId;
    }

    public void setProducerClientId(String producerClientId) {
        this.producerClientId = producerClientId;
    }

    public String getProducerDefaultTopic() {
        return producerDefaultTopic;
    }

    public void setProducerDefaultTopic(String producerDefaultTopic) {
        this.producerDefaultTopic = producerDefaultTopic;
    }

    public String getConsumerClientId() {
        return consumerClientId;
    }

    public void setConsumerClientId(String consumerClientId) {
        this.consumerClientId = consumerClientId;
    }

    public String getConsumerDefaultTopic() {
        return consumerDefaultTopic;
    }

    public void setConsumerDefaultTopic(String consumerDefaultTopic) {
        this.consumerDefaultTopic = consumerDefaultTopic;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public void setWillTopic(String willTopic) {
        this.willTopic = willTopic;
    }

    /**
     * MQTT连接器选项,主要都是 mqtt 的连接选项
     *
     * @return {@link org.eclipse.paho.client.mqttv3.MqttConnectOptions}
     */
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 设置连接的用户名
        options.setUserName(username);
        // 设置连接的密码
        options.setPassword(password.toCharArray());
        options.setServerURIs(new String[] {this.url});
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
        options.setWill("willTopic", WILL_DATA, 1, false);
        return options;
    }

    /**
     * MQTT 客户端的连接工厂
     *
     * @return {@link org.springframework.integration.mqtt.core.MqttPahoClientFactory}
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    /**
     * MQTT信息通道,发送消息（生产者）
     *
     * @return {@link org.springframework.messaging.MessageChannel}
     */
    @Bean(name = CHANNEL_NAME_OUT)
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * MQTT信息通道,获取消息的消息通道（消费者）
     *
     * @return {@link org.springframework.messaging.MessageChannel}
     */
    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    /**
     * 设置发送消息的处理器
     * 
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(this.producerClientId, mqttClientFactory());
        // 设置异步发送,这样发送消息时候不会阻塞
        messageHandler.setAsync(true);
        // 设置发送消息的主题
        messageHandler.setDefaultTopic(producerDefaultTopic);
        return messageHandler;
    }

    /**
     * 消费者信息
     * 
     * @return
     */
    @Bean
    public MessageProducer inbound() {
        // 配置消费者的信息
        MqttPahoMessageDrivenChannelAdapter adapter =
            new MqttPahoMessageDrivenChannelAdapter(this.consumerClientId, mqttClientFactory(),
                this.willTopic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        // 设置发送的信息通道
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    /**
     * 接收信息的处理方式
     * 
     * ServiceActivator 是表明当前方法用于处理 mqtt 的信息, inputChannel 用于指定用来消费消息的 channel
     * 
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    public MessageHandler handler() {
        return message -> {
            String payload = message.getPayload().toString();
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            LOGGER.info("处理的信息---------------------------");
            LOGGER.info("topic: " + topic);
            LOGGER.info("payload: " + payload);
            LOGGER.info("处理完信息---------------------------");
        };
    }

}
