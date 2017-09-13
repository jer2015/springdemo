package cn.tom.web.util.mq.core;

import cn.tom.web.util.mq.listener.BaseChannelListener;
import cn.tom.web.util.mq.listener.BaseListener;
import cn.tom.web.util.mq.util.LisenerStauts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import java.util.List;

public class ListenerDeclare {
	private Logger log = LoggerFactory.getLogger(ListenerDeclare.class);
	public int DEFAULT_CONSUMER_SIZE = 1;
	private String[] queueNames;
	private int consumerSize;
	private BaseListener baseListener;
	private BaseChannelListener baseChannelListener;
	private ConnectionFactory connectionFactory;

	public ListenerDeclare(ConnectionFactory connectionFactory, List<String> queueNameList) {
		this.connectionFactory = connectionFactory;
		this.queueNames = new String[queueNameList.size()];
		queueNameList.toArray(this.queueNames);
	}

	public ListenerDeclare(ConnectionFactory connectionFactory, List<String> queueNameList, int consumerSize) {
		this.connectionFactory = connectionFactory;
		this.queueNames = new String[queueNameList.size()];
		queueNameList.toArray(this.queueNames);
		this.consumerSize = consumerSize;
	}

	public ListenerDeclare(ConnectionFactory connectionFactory, List<String> queueNameList, BaseListener baseListener) {
		this.connectionFactory = connectionFactory;
		this.queueNames = new String[queueNameList.size()];
		queueNameList.toArray(this.queueNames);
		this.consumerSize = DEFAULT_CONSUMER_SIZE;
		this.baseListener = baseListener;
	}

	public ListenerDeclare(ConnectionFactory connectionFactory, List<String> queueNameList, BaseChannelListener baseChannelListener) {
		this.connectionFactory = connectionFactory;
		this.queueNames = new String[queueNameList.size()];
		queueNameList.toArray(this.queueNames);
		this.consumerSize = DEFAULT_CONSUMER_SIZE;
		this.baseChannelListener = baseChannelListener;
	}

	public ListenerDeclare(ConnectionFactory connectionFactory, List<String> queueNameList, int consumerSize, BaseListener baseListener) {
		this.connectionFactory = connectionFactory;
		this.queueNames = new String[queueNameList.size()];
		queueNameList.toArray(this.queueNames);
		this.consumerSize = consumerSize;
		this.baseListener = baseListener;
	}

	public ListenerDeclare(ConnectionFactory connectionFactory, List<String> queueNameList, int consumerSize, BaseChannelListener baseChannelListener) {
		this.connectionFactory = connectionFactory;
		this.queueNames = new String[queueNameList.size()];
		queueNameList.toArray(this.queueNames);
		this.consumerSize = consumerSize;
		this.baseChannelListener = baseChannelListener;
	}
	
	public boolean declareLisener(ThreadPoolTaskExecutor poolTaskScheduler){
		if(baseListener==null&&baseChannelListener==null){
			log.error("baseListener and baseChannelListener is empty");
			return false;
		}
		if(baseListener!=null&&baseChannelListener!=null){
			log.error("baseListener and baseChannelListener must be one that cannot be empty");
			return false;
		}
		Object obj = null;
		if(baseListener != null){
			obj = baseListener;
		}else{
			obj = baseChannelListener;
		}

		Assert.notNull(connectionFactory, "rabbitmq connectionFactory is null");
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setConcurrentConsumers(consumerSize);
		container.setTaskExecutor(poolTaskScheduler);
		container.setAutoStartup(true);
		container.setMessageListener(obj);
		container.setQueueNames(queueNames);
		container.afterPropertiesSet();
		LisenerStauts status = LisenerFactory.instantiation().keepLisener(connectionFactory, container);
		if(status.getCode()==0){
			container.start();
			return true;
		}else{
			log.error(status.getMsg());
			return false;
		}
	}
	
	public boolean changeLisener(int consumerSize){
		String key = getListenerKey();
		SimpleMessageListenerContainer listenerContainer = LisenerFactory.instantiation().getLisener(key);
		if(listenerContainer == null){
			log.error("not find queue:{}",key);
			return false;
		}
		listenerContainer.setConcurrentConsumers(consumerSize);
		listenerContainer.stop();
		listenerContainer.start();
		return true;
	}
	
	public boolean lisenerStart(){
		String key = getListenerKey();
		SimpleMessageListenerContainer listenerContainer = LisenerFactory.instantiation().getLisener(key);
		if(listenerContainer == null){
			log.error("not find queue:{}",key);
			return false;
		}
		listenerContainer.setTxSize(1);
		listenerContainer.start();
		return true;
	}
	
	public boolean lisenerShutdown(){
		String key = getListenerKey();
		SimpleMessageListenerContainer listenerContainer = LisenerFactory.instantiation().getLisener(key);
		if(listenerContainer == null){
			log.error("not find queue:{}",key);
			return false;
		}
		listenerContainer.setTxSize(0);
		return true;
	}

	public String getListenerKey() {
		String host = connectionFactory.getHost();
		StringBuffer keySb = new StringBuffer();
		keySb.append(host);
		if(queueNames != null && queueNames.length > 0){
			for(String queueName : queueNames){
				keySb.append("[").append(queueName).append("]");
			}
		}
		return keySb.toString();
	}
}
