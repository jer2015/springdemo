package cn.tom.web.util.mq.core;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.AMQP.Queue.DeleteOk;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueDeclare {
	private boolean DEFAULT_LASTING = Boolean.TRUE.booleanValue();
	private Logger log = LoggerFactory.getLogger(QueueDeclare.class);
	private String queueName;
	private boolean lasting;

	private ConnectionFactory connectionFactory;
	public QueueDeclare(String queueName,boolean lasting) {
		this.queueName = queueName;
		this.lasting = lasting;
	}

	public QueueDeclare(String queueName,ConnectionFactory connectionFactory){
		this.queueName = queueName;
		this.lasting = DEFAULT_LASTING;
		this.connectionFactory = connectionFactory;
	}

	public boolean declareQueue(){
		Connection conn = ConnectionForFactory.getConn(this.connectionFactory);
		Channel channel = conn.createChannel(false);
		try {
			DeclareOk ok = channel.queueDeclare(queueName, lasting, false, false, null);
			log.info("create queue:{} success ,ConsumerCount:{} MessageCount:{}",new Object[]{ok.getQueue(),ok.getConsumerCount()+"",ok.getMessageCount()+""});
			return true;
		} catch (IOException e) {
			log.error("create queue {} fail",queueName,e);
			return false;
		}finally{
			try {
				channel.close();
			} catch (IOException | TimeoutException e) {
				log.error("close channel fail",e);
			}
		}
	}
	
	public void convertAndSend(AmqpTemplate template,Object obj){
		template.convertAndSend(queueName,obj);
	}

	public boolean deleteQueue(){
		Connection conn = ConnectionForFactory.getConn(connectionFactory);
		Channel channel = conn.createChannel(false);
		try {
			DeleteOk ok = channel.queueDelete(queueName);
			log.info("delete queue:{} success ,{} count data deleted at the same time",new Object[]{queueName,ok.getMessageCount()+""});
			return true;
		} catch (IOException e) {
			log.error("delete queue:{} fail",queueName,e);
			return false;
		}
	}
}
