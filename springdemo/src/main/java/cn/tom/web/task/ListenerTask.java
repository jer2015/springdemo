package cn.tom.web.task;

import cn.tom.web.handler.QueueHandler;
import cn.tom.web.util.mq.core.LisenerDeclare;
import cn.tom.web.util.mq.core.LisenerFactory;
import cn.tom.web.util.mq.util.RabbitQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author lxl
 */
@Component
public class ListenerTask {
    private static  final Logger LOGGER = LoggerFactory.getLogger(ListenerTask.class);

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private ThreadPoolTaskExecutor poolTaskScheduler;

    @Autowired
    private QueueHandler queueHandler;

    public void run() {
        LOGGER.info("queue listener task");
        int listenerSize = 5;
        LisenerFactory factory = LisenerFactory.instantiation();
        Map<String,SimpleMessageListenerContainer> map = factory.getAllLiseners();
        ArrayList<RabbitQueue> queueList = new ArrayList<>();
        RabbitQueue rabbitQueue = new RabbitQueue();
        rabbitQueue.setQueue_name("test_queue");
        queueList.add(rabbitQueue);
        if(queueList != null && queueList.size() > 0){
            for(RabbitQueue queue : queueList){
                if(map == null || map.get(factory.getLisenerMapKey(connectionFactory,queue.getQueue_name())) == null) {
                    LOGGER.info("队列:{}开始启动监听",queue.getQueue_name());
                    List<String> queueNameList = new ArrayList<>();
                    queueNameList.add(queue.getQueue_name());
                    LisenerDeclare listener = new LisenerDeclare(connectionFactory, queueNameList, listenerSize, queueHandler);
                    boolean b = listener.declareLisener(poolTaskScheduler);
                    LOGGER.info("队列:{}启动监听,结果:{}", queue.getQueue_name(), b);
                }
            }
        }
    }
}
