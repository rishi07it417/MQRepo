package com.test.activemqMessageConsumer.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MQMessageListener implements MessageListener {

	public void onMessage(Message msg) {
		 if (msg instanceof TextMessage) {
             TextMessage textMessage = (TextMessage) msg;
             String text;
			try {
				text = textMessage.getText();
	             System.out.println("Received: " + text);

			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       } else {
             System.out.println("Received: " + msg);
      }
	}

}
