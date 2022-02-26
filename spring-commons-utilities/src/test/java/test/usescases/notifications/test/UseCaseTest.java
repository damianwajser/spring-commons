package test.usescases.notifications.test;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.usescases.notifications.configurations.NotificationConfiguration;
import test.usescases.notifications.model.Notification;
import test.usescases.notifications.senders.SenderFactory;

import java.io.IOException;
import java.nio.charset.Charset;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UseCaseTest {

	@Value("classpath:data/data.json")
	Resource resourceFile;

	@Autowired
	private SenderFactory senderFactory;

	@Autowired
	private FactoryCriteriaJsonBased<NotificationConfiguration> factory;

	@Test
	public void checkRegexTwoRepetition() throws InstantiationException, IllegalAccessException, IOException {
		String json = getJson();
		factory.applyCriteria(json).stream().map(n->this.getNotification(n,json)).forEach(n-> senderFactory.getPushSender(n.getSender().getChanel()).send(n));
	}

	private String getJson() throws IOException {
		return IOUtils.toString(resourceFile.getInputStream(), Charset.defaultCharset());
	}

	private Notification getNotification(NotificationConfiguration c, String json) {
		try {
			return c.getParser().parse(json, Notification.class).attachSender(c.getSender());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
