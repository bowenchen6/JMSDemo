package com.bowen.bean.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.bowen.bean.MqBean;

//������Ϣ�ķ���:
public class QueueSender {
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection;
		Session session;
		Destination destination;
		MessageProducer producer;
		//�������ӹ���
		connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://127.0.0.1:61616");
		try {
			//�������Ӷ���
			connection = connectionFactory.createConnection();
			//��ʼ��������
			connection.start();
			//��һ���������Ƿ�����������Ϣ������Ϊtrue,�ڶ���������Ч
			//�ڶ���������
			//Session.AUTO_ACKNOWLEDGEΪ�Զ�ȷ�ϣ��ͻ��˷��ͺͽ�����Ϣ����Ҫ������Ĺ������쳣Ҳ��ȷ����Ϣ��Ӧ������ִ��֮ǰȷ�ϵ�
			//Session.CLIENT_ACKNOWLEDGEΪ�ͻ���ȷ�ϡ��ͻ��˽��յ���Ϣ�󣬱������javax.jms.Message��acknowledge������jms�������Ż�ɾ����Ϣ��������ʧ�ܵ�
			//ʱ��ȷ����Ϣ,��ȷ�ϵĻ������Ƴ����У�һֱ���ڣ��´������������ܡ�������Ϣ�����Ӳ��Ͽ���������������Ҳ������ܣ���������¶���ģʽ���������������ߣ�
			//DUPS_OK_ACKNOWLEDGE��������ȷ��ģʽ��һ�����շ�Ӧ�ó���ķ������ôӴ�����Ϣ�����أ��Ự����ͻ�ȷ����Ϣ�Ľ��գ����������ظ�ȷ�ϡ�����Ҫ������Դʹ��ʱ������ģʽ�ǳ���Ч��
			//������
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			//������������test-queue�Ķ���Դ
			destination = session.createQueue("test-queue");
			//�������е�������
			producer = session.createProducer(destination);
			//���ò��־û�
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			MqBean bean = new MqBean();
			bean.setAge(13);
			for(int i=0;i<100;i++){
				bean.setName("С��"+i);
				//���Ͷ�����Ϣ
				ObjectMessage objectMessage=session.createObjectMessage(bean);
				MqBean m=(MqBean) objectMessage.getObject();
				System.out.println("���͵Ķ�����Ϣ�ǣ�"+m.getName());
				producer.send(objectMessage);
				//10�뷢����һ����Ϣ
				Thread.sleep(100000);
			}
			producer.close();
			System.out.println("��Ϣ�������");
		} catch (JMSException e) {
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
