package cn.tom.web.util.mq.core;

import cn.tom.web.util.mq.util.ListenerStauts;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

public class LisenerFactory {
	private static LisenerFactory lisenerFactory = null;
	private Map<String,SimpleMessageListenerContainer> lisenerMap = new HashMap<String,SimpleMessageListenerContainer>();
	
	public static LisenerFactory instantiation(){
		if(lisenerFactory == null){
			lisenerFactory = new LisenerFactory();
			return lisenerFactory;
		}else{
			return lisenerFactory;
		}
	}

    public ListenerStauts keepListener(ConnectionFactory connectionFactory, SimpleMessageListenerContainer listenerContainer) {
        String key = getListenerMapKey(connectionFactory, listenerContainer);
        Object obj = lisenerMap.get(key);
		if(obj != null){
            return ListenerStauts.isExit;
        }else{
			lisenerMap.put(key, listenerContainer);
            return ListenerStauts.success;
        }
	}

    public SimpleMessageListenerContainer getListener(String key) {
        return lisenerMap.get(key);
	}

    public Map<String, SimpleMessageListenerContainer> getAllListeners() {
        if(lisenerMap != null){
			return lisenerMap;
		}
		return null;
	}

    public String getListenerMapKey(ConnectionFactory connectionFactory, SimpleMessageListenerContainer listenerContainer) {
        String host = connectionFactory.getHost();
		String[] queueNames = listenerContainer.getQueueNames();
		StringBuffer keySb = new StringBuffer();
		keySb.append(host);
		if(queueNames != null && queueNames.length > 0){
			for(String queueName : queueNames){
				keySb.append("[").append(queueName).append("]");
			}
		}
		return keySb.toString();
	}

    public String getListenerMapKey(ConnectionFactory connectionFactory, String queueName) {
        String host = connectionFactory.getHost();
		StringBuffer keySb = new StringBuffer();
		keySb.append(host);
		keySb.append("[").append(queueName).append("]");
		return keySb.toString();
	}
}
