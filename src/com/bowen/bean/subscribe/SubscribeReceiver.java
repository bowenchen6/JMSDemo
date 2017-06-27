package com.bowen.bean.subscribe;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.bowen.bean.MqBean;

//������Ϣ�Ľ���
public class SubscribeReceiver {

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		// Connection ��JMS �ͻ��˵�JMS Provider ������  
		Connection connection = null;
		// Session�� һ�����ͻ������Ϣ���߳�  
		Session session;
		// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.  
		Destination destination;
		// �����ߣ���Ϣ������  
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
		try {
			// ����ӹ����õ����Ӷ���  
			connection = connectionFactory.createConnection();
			// ����  
			connection.start();
			// ��ȡ��������  
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����  
			destination = session.createQueue("test-queue");
			consumer = session.createConsumer(destination);
			 boolean isContinue = true;
			 while(isContinue){
				 TextMessage msg=(TextMessage) consumer.receive();
				 if (msg.getText().equals("end")) {
		                isContinue = false;
		                System.out.println("�յ��˳���Ϣ������Ҫ�˳���");
		            } 
			 }
			  System.out.println("�����˳��ˣ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
