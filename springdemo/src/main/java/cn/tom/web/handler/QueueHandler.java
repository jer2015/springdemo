package cn.tom.web.handler;

import cn.tom.web.util.mq.listener.BaseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * @author lxl
 */
@Component(value = "queueHandler")
public class QueueHandler implements BaseListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueHandler.class);
    @Override
    public void onMessage(Message message) {
        LOGGER.info("mq 监听到数据:{}", new String(message.getBody()));
    }
}

