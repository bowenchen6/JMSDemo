package com.bowen.bean.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.bowen.bean.MqBean;

//������Ϣ�Ľ���
public class QueueReceiver {
	
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
			//������Ϣ�������߶���
			consumer = session.createConsumer(destination);
			//������Ϣ����
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					try {
						MqBean bean = (MqBean) ((ObjectMessage)message).getObject();
						System.out.println(bean.getName());
						if (null !=bean ||!"".equals(bean)) {
							System.out.println("�յ���Ϣ" + bean.getName());
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.close();
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
