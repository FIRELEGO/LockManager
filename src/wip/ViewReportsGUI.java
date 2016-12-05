package wip;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewReportsGUI extends GUI {
	private TableView<Report> tvReport = new TableView<Report>();

	private Button btnBack = new MyButton("Back");
	
	public ViewReportsGUI() {
		super(500, 600, "View Reports");

		TableColumn<Report, Integer> tcReportId = new TableColumn<Report, Integer>("ReportId");
		TableColumn<Report, Integer> tcLockSerial = new TableColumn<Report, Integer>("Lock Serial");
		TableColumn<Report, String> tcPriority = new TableColumn<Report, String>("Priority");
		TableColumn<Report, String> tcDate = new TableColumn<Report, String>("Date");
		TableColumn<Report, String> tcProgress = new TableColumn<Report, String>("Progress");
		TableColumn<Report, String> tcComplaint = new TableColumn<Report, String>("Complaint");

		tcReportId.setCellValueFactory(new PropertyValueFactory<Report, Integer>("reportId"));
		tcLockSerial.setCellValueFactory(new PropertyValueFactory<Report, Integer>("serial"));
		tcPriority.setCellValueFactory(new PropertyValueFactory<Report, String>("priority"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Report, String>("date"));
		tcProgress.setCellValueFactory(new PropertyValueFactory<Report, String>("progress"));
		tcComplaint.setCellValueFactory(new PropertyValueFactory<Report, String>("complaint"));

		tvReport.getColumns().add(tcReportId);
		tvReport.getColumns().add(tcLockSerial);
		tvReport.getColumns().add(tcPriority);
		tvReport.getColumns().add(tcDate);
		tvReport.getColumns().add(tcProgress);
		tvReport.getColumns().add(tcComplaint);
		
		tvReport.setItems(Main.getReports());
		
		gpMain.add(tvReport, 0, 1, 2, 1);
		
		btnBack.setOnAction(e -> Main.setStage("Home"));
		
		gpButtons.add(btnBack, 0, 0);
	}
}
