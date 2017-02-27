package wip;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportGUI extends GUI {
	private Label lblSerial = new Label("Lock Serial");
	private TextField txtSerial = new MyTextField(120);
	private Label lblPriority = new Label("Priority");
	private ComboBox<String> cbPriority = new ComboBox<String>();
	private Label lblComplaint = new Label("Complaint");
	private TextArea txtComplaint = new TextArea();

	private Label lblLength = new Label("Characters: 0/500");

	private Button btnSubmit = new MyButton("Submit");
	private Button btnClear = new MyButton("Clear");
	private Button btnBack = new MyButton("Back");

	public ReportGUI() {
		super(500, 600, "Report Lock");

		gpMain.add(lblSerial, 0, 0);
		gpMain.add(txtSerial, 1, 0);
		gpMain.add(lblPriority, 2, 0);
		cbPriority.getItems().addAll("Low", "Medium", "High");
		gpMain.add(cbPriority, 3, 0);

		gpMain.add(lblComplaint, 0, 1);

		txtComplaint.setMinHeight(100);
		txtComplaint.setMaxHeight(100);
		txtComplaint.setMinWidth(300);
		txtComplaint.setMaxWidth(300);
		txtComplaint.setWrapText(true);
		txtComplaint.setOnKeyTyped(e -> updateLength());
		gpMain.add(txtComplaint, 1, 1, 3, 1);

		gpMain.add(lblLength, 1, 2);


		gpButtons.add(btnSubmit, 0, 0);
		gpButtons.add(btnClear, 1, 0);
		gpButtons.add(btnBack, 2, 0);

		btnSubmit.setOnAction(e -> submit());
		btnClear.setOnAction(e -> clear());
		btnBack.setOnAction(e -> Main.setStage("Home"));
		
		enterBtn = btnSubmit;
	}

	private void updateLength()	{
		lblLength.setText("Characters: " + (txtComplaint.getLength() + 1) + "/500");
	}

	// Submits the forms
	private void submit() {
		lblError.setText("");
		lblSuccess.setText("");

		if(txtComplaint.getLength() > 500) {
			lblError.setText("Complaint is over 500 characaters.");
		} else {
			try {
				int serial = Integer.parseInt(txtSerial.getText());

				if(cbPriority.getSelectionModel().getSelectedItem() == null) {
					lblError.setText("Select a priority level.");
				} else if(Main.searchSerial(serial, false) == null) {
					lblError.setText("Check serial.");
				} else if(txtComplaint.getText().equals("")) {
					lblError.setText("Enter a complaint.");
				} else {
					Main.addReport(serial, cbPriority.getSelectionModel().getSelectedItem(), "SUBMITED", txtComplaint.getText());
					Main.log("Report filed.");
					
					clear();
					lblSuccess.setText("Report filed.");
				}
			} catch(NumberFormatException e) {
				lblError.setText("Check that serial is a number.");
			}
		}
	}

	// Clears the form
	private void clear() {
		txtSerial.setText("");
		cbPriority.getSelectionModel().clearSelection();
		txtComplaint.setText("");
		
		txtSerial.requestFocus();
	}
}
