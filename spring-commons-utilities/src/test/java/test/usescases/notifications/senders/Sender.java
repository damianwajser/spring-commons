package test.usescases.notifications.senders;

import test.usescases.notifications.model.Notification;

public interface Sender{
	void send(Notification n);
}
