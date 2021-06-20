package com.test.activemqMessageSender.config;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MQConfig {
	private static String brokerURL = "tcp://localhost:61616";
	private static ActiveMQConnectionFactory mqConnFactory = null;
	private static ActiveMQConnection mqconn = null;
	
	private MQConfig() {
		
	}

	public static ActiveMQConnection configMQConnection() throws JMSException {
		if(mqConnFactory == null) {
			mqConnFactory = new ActiveMQConnectionFactory(brokerURL);
		}
		if(mqconn == null || mqconn.isClosed()) {
			System.out.print("Creating new connection object....");
			mqconn = (ActiveMQConnection) mqConnFactory.createConnection();
		}else {
			System.out.print("connection object is already active....");

		}
		return mqconn;
	}
	
	public static void closeConnection() {
		try {
			mqconn.close();
			System.out.print("Connection Closed Successfully..");

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
