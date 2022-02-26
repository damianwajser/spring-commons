package test.usescases.notifications.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.usescases.notifications.senders.impl.EmailSender;
import test.usescases.notifications.senders.impl.PushSender;

@Component
public class SenderFactory {

	@Autowired
	private PushSender pushSender;

	@Autowired
	private EmailSender emailSender;

	public Sender getPushSender(Chanel chanel){
		Sender sender;
		switch (chanel){
			case EMAIL: sender = emailSender; break;
			case PUSH:
			default: sender = pushSender;
		}
		return sender;
	}
}
