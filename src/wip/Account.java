package wip;

public class Account {
	private String username;
	private String password;
	private String salt;
	private boolean awaitingAproval;
	private boolean approved;
	private int priviledge;

	public Account(String username, String password, String salt, boolean awaitingAproval, boolean approved, int priviledge) {
		super();
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.awaitingAproval = awaitingAproval;
		this.approved = approved;
		this.priviledge = priviledge;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	public boolean isAwaitingAproval() {
		return awaitingAproval;
	}

	public boolean isApproved() {
		return approved;
	}

	public int getPriviledge() {
		return priviledge;
	}
}
