package test.usescases.notifications.senders.impl;

import org.springframework.stereotype.Service;
import test.usescases.notifications.model.Notification;
import test.usescases.notifications.senders.Sender;

@Service
public class EmailSender implements Sender {

	@Override
	public void send(Notification n) {
		System.out.println("send push notifications to: " + n.getUserid() + " with message: " + n);
	}

}
