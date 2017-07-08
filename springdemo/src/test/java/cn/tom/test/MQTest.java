package cn.tom.test;

import cn.tom.web.handler.QueueHandler;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:applicationContext.xml")
public class MQTest {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private ThreadPoolTaskExecutor poolTaskScheduler;

    @Autowired
    private QueueHandler queueHandler;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendMsgTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aa", "123");
        amqpTemplate.convertAndSend("orderPayQueryQueue", jsonObject.toJSONString());
        System.out.println("success");
    }

    @Test
    public void chanelTest() throws IOException {
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(true);
        HashMap<String, Object> arguments = new HashMap<>();
        channel.exchangeDeclare("fpj.exchange", "direct");
        arguments.put("x-dead-letter-exchange", "amq.direct");
        arguments.put("x-message-ttl", 10000);
        channel.queueDeclare("orderPayQueryQueue", false, false, false, arguments);
        channel.exchangeBind("orderPayQueryQueue", "fpj.exchange", null);


        channel.exchangeDeclare("some.exchange.name", "direct");
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "fpj.exchange");
        channel.queueDeclare("myqueue", false, false, false, args);
        channel.exchangeBind("myqueue", "fpj.exchange", null);
    }

    @Test
    public void queueTest() {

    }
}
