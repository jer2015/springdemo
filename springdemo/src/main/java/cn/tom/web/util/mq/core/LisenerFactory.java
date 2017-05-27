package cn.tom.web.util.mq.core;

import cn.tom.web.util.mq.util.LisenerStauts;
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
	
	public LisenerStauts keepLisener(ConnectionFactory connectionFactory, SimpleMessageListenerContainer listenerContainer){
		String key = getLisenerMapKey(connectionFactory,listenerContainer);
		Object obj = lisenerMap.get(key);
		if(obj != null){
			return LisenerStauts.isExit;
		}else{
			lisenerMap.put(key, listenerContainer);
			return LisenerStauts.success;
		}
	}
	
	public SimpleMessageListenerContainer getLisener(String key){
		return lisenerMap.get(key);
	}
	
	public Map<String, SimpleMessageListenerContainer> getAllLiseners(){
		if(lisenerMap != null){
			return lisenerMap;
		}
		return null;
	}

	public String getLisenerMapKey(ConnectionFactory connectionFactory,SimpleMessageListenerContainer listenerContainer){
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
	public String getLisenerMapKey(ConnectionFactory connectionFactory,String queueName){
		String host = connectionFactory.getHost();
		StringBuffer keySb = new StringBuffer();
		keySb.append(host);
		keySb.append("[").append(queueName).append("]");
		return keySb.toString();
	}
}
