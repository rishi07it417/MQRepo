package com.test.ibmmqMessageReceiver.config;

import javax.jms.JMSException;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.Connection;

public class MQConfig {
	  private static String host = "localhost";
	  private static int port = 1414;
	  private static String channel = "SYSTEM.DEF.SVRCONN";
	  private static String user = "Admin";
	  private static String password = "111";
	  private static String queueManagerName = "test";
	  private static Connection mqconn = null;

	  // System exit status value (assume unset value to be 1)
	  private static int status = 1;
	
	private MQConfig() {
		
	}

	public static Connection configMQConnection() throws JMSException {
		JmsFactoryFactory jmsF = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		JmsConnectionFactory jmsCF = jmsF.createConnectionFactory();
		
		jmsCF.setStringProperty(WMQConstants.WMQ_HOST_NAME, host);
		jmsCF.setIntProperty(WMQConstants.WMQ_PORT, port);
		jmsCF.setStringProperty(WMQConstants.WMQ_CHANNEL, channel);
		jmsCF.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, queueManagerName);
		jmsCF.setStringProperty(WMQConstants.USERID, user);
		jmsCF.setStringProperty(WMQConstants.PASSWORD, password);
		jmsCF.setStringProperty(WMQConstants.PASSWORD, password);
		jmsCF.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		
		mqconn = jmsCF.createConnection();


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
