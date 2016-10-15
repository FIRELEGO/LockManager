package wip;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomeGUI extends GUI {
	private Button btnLookUp = new MyButton("Look Up");
	private Button btnAddLock = new MyButton("Add Lock");
	private Button btnEditLock = new MyButton("Edit Lock");
	private Button btnUnlockLock = new MyButton("Unlock Lock");
	private Button btnAssignLocker = new MyButton("Assign Locker");
	private Button btnAssignBarcode = new MyButton("Assign Barcode");
	private Button btnReportLock = new MyButton("Report Lock");
	private Button btnDeleteLock = new MyButton("Delete Lock");
	private Button btnPurgeLocks = new MyButton("Purge Old Locks");
	private Button btnExit = new MyButton("Exit Program");

	public HomeGUI() {
		super(500, 400, "Home");
		
		// TODO not done functions
		btnEditLock.setDisable(true);
		btnUnlockLock.setDisable(true);
		btnReportLock.setDisable(true);
		btnDeleteLock.setDisable(true);
		
		gpButtons.add(btnLookUp, 0, 0);
		gpButtons.add(btnAddLock, 1, 0);
		gpButtons.add(btnEditLock, 2, 0);
		gpButtons.add(btnUnlockLock, 0, 1);
		gpButtons.add(btnAssignLocker, 1, 1);
		gpButtons.add(btnAssignBarcode, 2, 1);
		gpButtons.add(btnReportLock, 0, 2);
		gpButtons.add(btnDeleteLock, 1, 2);
		gpButtons.add(btnPurgeLocks, 2, 2);
		gpButtons.add(btnExit, 1, 3);

		btnLookUp.setOnAction(e -> Main.setStage("LookUp"));
		btnAddLock.setOnAction(e -> Main.setStage("AddLock"));
		btnEditLock.setOnAction(e -> Main.setStage("EditLock"));
		btnUnlockLock.setOnAction(e -> Main.setStage("UnlockLock"));
		btnAssignLocker.setOnAction(e -> Main.setStage("AssignLocker"));
		btnAssignBarcode.setOnAction(e -> Main.setStage("AssignBarcode"));
		btnReportLock.setOnAction(e -> Main.setStage("ReportLock"));
		btnDeleteLock.setOnAction(e -> Main.setStage("DeleteLock"));
		btnPurgeLocks.setOnAction(e -> Main.purgeLocks());
		btnExit.setOnAction(e -> Main.exit());
	}
}
