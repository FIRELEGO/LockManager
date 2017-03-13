package wip;

import javafx.scene.control.Button;

public class HomeGUI extends GUI {
	private int privilege = Main.user.getPrivilege();
	private Button btnLookUp = new MyButton("Look Up");
	private Button btnAddLock = new MyButton("Add Lock");
	private Button btnEditLock = new MyButton("Edit Lock");
	private Button btnUnlockLock = new MyButton("Unlock Lock");
	private Button btnAssignLocker = new MyButton("Assign Locker");
	private Button btnAssignBarcode = new MyButton("Assign Barcode");
	private Button btnReportLock = new MyButton("Report Lock");
	private Button btnDeleteLock = new MyButton("Delete Lock");
	private Button btnPurgeLocks = new MyButton("Purge Old Locks");
	private Button btnViewReports = new MyButton("View Reports");
	private Button btnGenerate = new MyButton("Generate Data Sheet");
	private Button btnList = new MyButton("List");
	private Button btnReleaseAllLocker = new MyButton("Release All Lockers");
	private Button btnExit = new MyButton("Exit Program");

	public HomeGUI() {
		super(500, 400, "Home");
		
		gpButtons.add(btnLookUp, 0, 0);
		gpButtons.add(btnAddLock, 1, 0);
		gpButtons.add(btnEditLock, 2, 0);
		gpButtons.add(btnUnlockLock, 0, 1);
		gpButtons.add(btnAssignLocker, 1, 1);
		gpButtons.add(btnAssignBarcode, 2, 1);
		gpButtons.add(btnReportLock, 0, 2);
		gpButtons.add(btnDeleteLock, 1, 2);
		gpButtons.add(btnPurgeLocks, 2, 2);
		gpButtons.add(btnViewReports, 0, 3);
		gpButtons.add(btnGenerate, 1, 3);
		gpButtons.add(btnList, 2, 3);
		gpButtons.add(btnReleaseAllLocker, 1, 4);
		gpButtons.add(btnExit, 1, 5);
		
		// 0 = user (maybe student), 1 = teacher, 2 = school admin, 3 = program admin/manager
		if(privilege < 2) {
			btnEditLock.setDisable(true);
			btnDeleteLock.setDisable(true);
			btnPurgeLocks.setDisable(true);
			btnReleaseAllLocker.setDisable(true);
		}

		btnLookUp.setOnAction(e -> Main.setStage("LookUp"));
		btnAddLock.setOnAction(e -> Main.setStage("AddLock"));
		btnEditLock.setOnAction(e -> Main.setStage("EditLock"));
		btnUnlockLock.setOnAction(e -> Main.setStage("UnlockLock"));
		btnAssignLocker.setOnAction(e -> Main.setStage("AssignLocker"));
		btnAssignBarcode.setOnAction(e -> Main.setStage("AssignBarcode"));
		btnReportLock.setOnAction(e -> Main.setStage("ReportLock"));
		btnDeleteLock.setOnAction(e -> Main.setStage("DeleteLock"));
		btnPurgeLocks.setOnAction(e -> Main.purgeLocks());
		btnViewReports.setOnAction(e -> Main.setStage("ViewReports"));
		btnGenerate.setOnAction(e -> Main.setStage("Generate"));
		btnList.setOnAction(e -> Main.setStage("List"));
		btnReleaseAllLocker.setOnAction(e -> Main.releaseAllLockers());
		btnExit.setOnAction(e -> Main.exit());
	}
}
