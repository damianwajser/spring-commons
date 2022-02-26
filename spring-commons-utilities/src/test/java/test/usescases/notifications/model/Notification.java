package test.usescases.notifications.model;

public class Notification {

	private String userid;
	private String title;
	private String subtitle;
	private String body;
	private String footer;
	private SenderModel sender;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public Notification attachSender(SenderModel sender) {
		this.sender = sender;
		return this;
	}

	public SenderModel getSender(){
		return this.sender;
	}

	@Override
	public String toString() {
		return "Notification{" +
				"userid='" + userid + '\'' +
				", title='" + title + '\'' +
				", subtitle='" + subtitle + '\'' +
				", body='" + body + '\'' +
				", footer='" + footer + '\'' +
				'}';
	}
}
