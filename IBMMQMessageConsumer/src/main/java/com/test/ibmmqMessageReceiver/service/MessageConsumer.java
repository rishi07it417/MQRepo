package com.test.ibmmqMessageReceiver.service;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import com.test.ibmmqMessageReceiver.config.MQConfig;

public class MessageConsumer {

	public void consumeMessage(String destination, String destinationType) {
		Session ses = null;
		Message msg = null;
		Connection conn =null;
		javax.jms.MessageConsumer msgConsumer = null;
		try {
			conn = MQConfig.configMQConnection();
			System.out.println("Destination ::"+destination);
			System.out.println("Destination Type::"+destinationType);

			System.out.println("Connection Started for incoming messge..");
			if("Topic".equals(destinationType)) {
				conn.setClientID("testtopic");
				ses = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
				Topic topic = ses.createTopic(destination);
				msgConsumer = ses.createDurableConsumer(topic, "testtopc");
				 conn.start();
				 while(true) {
						msg = msgConsumer.receive(5000);
						 if (msg instanceof TextMessage) {
			                    TextMessage textMessage = (TextMessage) msg;
			                    String text = textMessage.getText();
			                    System.out.println("Received: " + text);
			              } else {
			                    System.out.println("Received: " + msg);
			             }	
					}
					
				
			}else {
				ses = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
				Queue que = ses.createQueue(destination);
				msgConsumer = ses.createConsumer(que);
				conn.start();
				msg = msgConsumer.receive(5000);
				 if (msg instanceof TextMessage) {
	                    TextMessage textMessage = (TextMessage) msg;
	                    String text = textMessage.getText();
	                    System.out.println("Received: " + text);
	              } else {
	                    System.out.println("Received: " + msg);
	             }	
			}
			
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.stop();
				System.out.println("Connection Stopped..");
				msgConsumer.close();
				System.out.println("Consumer is closed..");
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ses !=null) {
				try {
					ses.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String args[]) {
		MessageConsumer receiver = new MessageConsumer();
		//receiver.consumeMessage("local.q.1", "Queue");
		receiver.consumeMessage("testtopc", "Topic");

		MQConfig.closeConnection();
		
	}
}
