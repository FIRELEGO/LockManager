package wip;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginGUI extends GUI {
	private Label lblUsername = new Label("Username: ");
	private TextField txtUsername = new TextField("");
	private Label lblPassword = new Label("Password: ");
	private PasswordField txtPassword = new PasswordField();

	private Button btnSignin = new MyButton("Sign in");
	private Button btnExit = new MyButton("Exit");

	public LoginGUI() {
		super(500, 450, "Login");

		gpMain.add(lblUsername, 0, 0);
		gpMain.add(txtUsername, 1, 0);
		gpMain.add(lblPassword, 0, 1);
		gpMain.add(txtPassword, 1, 1);
		
		gpButtons.add(btnSignin, 0, 0);
		gpButtons.add(btnExit, 1, 0);

		btnSignin.setOnAction(e -> signIn());
		btnExit.setOnAction(e -> Main.exit());
		
		enterBtn = btnSignin;
	}
	
	private void signIn() {
			Account user = Main.getUser(txtUsername.getText());
			String pass = txtPassword.getText();


			lblError.setText("");

			if (pass.equals("")) {
				lblError.setText("Make sure all fields are filled.");
			} else if (user == null) {
				lblError.setText("User does not exist.");
			} else if (user.isAwaitingAproval()) {
				lblError.setText("User is awaiting approval.");
			} else if (!user.isApproved()) {
				lblError.setText("User is not approved.");
			} else {
				String salt = user.getSalt();
				
				if(Main.hash(pass + salt).equals(user.getPassword())) {
					Main.user = user;
					Main.log("Login");
					Main.setStage("Home");
				} else {
					lblError.setText("Incorrect password.");
				}
			}
	}
}
