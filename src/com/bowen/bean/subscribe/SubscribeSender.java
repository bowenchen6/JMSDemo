package com.bowen.bean.subscribe;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.bowen.bean.MqBean;

// ������Ϣ�ķ���
public class SubscribeSender {

	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection;
		Session session;
		Destination destination;
		MessageProducer producer;
		connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			destination = session.createTopic("test-topic");
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			for(int i=0;i<100;i++){
				Thread.sleep(1000);
				TextMessage tm=session.createTextMessage("��ֵĵ�"+i+"��");
				System.out.println("���͵���Ϣ�ǣ�"+tm.getText());
				producer.send(tm);
			}
			producer.close();
			System.out.println("��Ϣ������ϣ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
