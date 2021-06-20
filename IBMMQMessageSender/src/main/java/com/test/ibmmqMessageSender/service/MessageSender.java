package com.test.ibmmqMessageSender.service;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import com.test.ibmmqMessageSender.config.MQConfig;

public class MessageSender {

	public void sendMessage(String msg, String destination, String destinationType) {
		MessageProducer msgProducer = null;
		TextMessage txtMsg = null;
		Session ses = null;
		try {
			Connection conn = MQConfig.configMQConnection();
			ses = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			System.out.println("Message To Send::"+msg);
			System.out.println("Destination ::"+destination);
			System.out.println("Destination Type::"+destinationType);


			if("Topic".equals(destinationType)) {
				Topic topic = ses.createTopic(destination);
				msgProducer = ses.createProducer(topic);
				txtMsg = ses.createTextMessage(msg);
				msgProducer.send(txtMsg);
				System.out.println("::Message Successfully Sent to Topic ::");
			}else {
				Queue que = ses.createQueue(destination);
				msgProducer = ses.createProducer(que);
				txtMsg = ses.createTextMessage(msg);
				msgProducer.send(txtMsg);
				System.out.println("::Message Successfully Sent to Queue ::");
			}
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				msgProducer.close();
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
		MessageSender sender = new MessageSender();
		//sender.sendMessage(":::::QUEUE msg 1:::::", "test.q1", "Queue");
		sender.sendMessage(":::::TOPIC msg 2 by Rishi:::::", "testtopc", "Topic");
	
		MQConfig.closeConnection();
		
	}
}
