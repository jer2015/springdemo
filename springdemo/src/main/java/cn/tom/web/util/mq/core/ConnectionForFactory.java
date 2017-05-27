package cn.tom.web.util.mq.core;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.util.Assert;

public class ConnectionForFactory {

	public static Connection getConn(ConnectionFactory connectionFactory){
		Assert.notNull(connectionFactory, "rabbitmq connectionFactory is null");
		Connection conn = connectionFactory.createConnection();
		Assert.state(conn.isOpen(), "have connection is closed");
		return conn;
	}
}
