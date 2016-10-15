/* Nicholas Signori
 * Bank
 */
package wip;

import java.util.ArrayList;

public class User {
	private ArrayList<Integer> accounts = new ArrayList<Integer>();
	private int ID;
	private String first;
	private String last;
	private String address;
	private String zip;
	private String state;
	private String password;
	private String salt;

	public User(int ID, String first, String last, String address, String zip, String state, String password, String salt, ArrayList<Integer> accounts) {
		this.ID = ID;
		this.first = first;
		this.last = last;
		this.address = address;
		this.zip = zip;
		this.state = state;
		this.password = password;
		this.salt = salt;
		this.accounts = accounts;
	}

	public void addAccount(int acctNum) {
		accounts.add(acctNum);
	}

	public ArrayList<Integer> getAccounts() {
		return accounts;
	}

	public void deleteAccount(int acctNum) {
		accounts.remove((Integer)acctNum);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getID() {
		return ID;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}
}
