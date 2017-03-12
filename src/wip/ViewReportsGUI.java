package wip;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
		TableColumn<Report, Boolean> tcComplaint = new TableColumn<Report, Boolean>("Complaint");

		tcReportId.setCellValueFactory(new PropertyValueFactory<Report, Integer>("reportId"));
		tcLockSerial.setCellValueFactory(new PropertyValueFactory<Report, Integer>("serial"));
		tcPriority.setCellValueFactory(new PropertyValueFactory<Report, String>("priority"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Report, String>("date"));
		tcProgress.setCellValueFactory(new PropertyValueFactory<Report, String>("progress"));
		tcComplaint.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Report, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Report, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		tcComplaint.setCellFactory(new Callback<TableColumn<Report, Boolean>, TableCell<Report, Boolean>>() {

			@Override
			public TableCell<Report, Boolean> call(TableColumn<Report, Boolean> p) {
				return new MessageButtonCell();
			}

		});

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
		
		Main.log("Lock reports viewed.");
	}
	
	public void message(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Lock Report");
		alert.setHeaderText("Complaint Message");
		alert.setContentText(message);

		alert.showAndWait();
	}
	

	// Creates a EditButtonCell that allows the exam to be edited
		private class MessageButtonCell extends TableCell<Report, Boolean> {
			final Button cellButton = new Button("View");

			MessageButtonCell() {

				cellButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {
						Report rep = tvReport.getItems().get(MessageButtonCell.this.getIndex());
						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Complaint");
						alert.setHeaderText("Lock Serial: " + rep.getSerial() + "\tReport date: " + rep.getDate());
						alert.setContentText(rep.getComplaint());

						alert.showAndWait();
					}
				});
			}

			@Override
			protected void updateItem(Boolean t, boolean empty) {
				super.updateItem(t, empty);
				if (!empty) {
					setGraphic(cellButton);
				} else {
					setGraphic(null);
				}
			}
		}
}
