package MQTT;

import java.util.concurrent.ScheduledExecutorService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
public class ClientMQTT {
    public static final String HOST = "tcp://121.40.244.52:1883";
    public static final String TOPIC = "quxiaochen";
    private static final String clientid = "clinet11";
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "admin";
    private String passWord = "public";
    private ScheduledExecutorService scheduler;
    private void start() {
        try {
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);
            client.setCallback(new PushCallBack());
            MqttTopic topic = client.getTopic(TOPIC);
            options.setWill(topic, "close".getBytes(), 2, true);
            client.connect(options);
            int[] Qos = { 0 };
            String[] topic1 = { TOPIC };
            client.subscribe(topic1, Qos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws MqttException {
        ClientMQTT client = new ClientMQTT();
        client.start();
    }
}

