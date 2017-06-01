package wip;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ViewReportsGUI extends GUI {
	private TableView<Report> tvReport = new TableView<Report>();

	private Button btnBack = new MyButton("Back");

	public ViewReportsGUI() {
		super(600, 600, "View Reports");

		TableColumn<Report, Integer> tcReportId = new TableColumn<Report, Integer>("ReportId");
		TableColumn<Report, Integer> tcLockSerial = new TableColumn<Report, Integer>("Lock Serial");
		TableColumn<Report, String> tcPriority = new TableColumn<Report, String>("Priority");
		TableColumn<Report, String> tcDate = new TableColumn<Report, String>("Date");
		TableColumn<Report, Boolean> tcComplaint = new TableColumn<Report, Boolean>("Complaint");
		TableColumn<Report, Boolean> tcResolve = new TableColumn<Report, Boolean>("Resolve");

		tcReportId.setCellValueFactory(new PropertyValueFactory<Report, Integer>("reportId"));
		tcLockSerial.setCellValueFactory(new PropertyValueFactory<Report, Integer>("serial"));
		tcPriority.setCellValueFactory(new PropertyValueFactory<Report, String>("priority"));
		tcDate.setCellValueFactory(new PropertyValueFactory<Report, String>("date"));
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
		tcResolve.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Report, Boolean>, ObservableValue<Boolean>>() {

					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Report, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});

		tcResolve.setCellFactory(new Callback<TableColumn<Report, Boolean>, TableCell<Report, Boolean>>() {

			@Override
			public TableCell<Report, Boolean> call(TableColumn<Report, Boolean> p) {
				return new ResolveButtonCell();
			}

		});

		tvReport.getColumns().add(tcReportId);
		tvReport.getColumns().add(tcLockSerial);
		tvReport.getColumns().add(tcPriority);
		tvReport.getColumns().add(tcDate);
		tvReport.getColumns().add(tcComplaint);
		tvReport.getColumns().add(tcResolve);

		tvReport.setItems(Main.getReports());

		gpMain.add(tvReport, 0, 1, 2, 1);

		btnBack.setOnAction(e -> Main.setStage("Home"));

		gpButtons.add(btnBack, 0, 0);

		Main.log("Lock reports viewed.");
	}

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
	
	private class ResolveButtonCell extends TableCell<Report, Boolean> {
		final Button cellButton = new Button("Resolve");

		ResolveButtonCell() {

			cellButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					Report rep = tvReport.getItems().get(ResolveButtonCell.this.getIndex());

					// Confirmation that user wants to purge
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Resolve Report");
					alert.setHeaderText("Resolve report?");
					alert.setContentText("Are you sure you want to resolve the report? This will delete it from the database.\nCick \"Ok\" to continue with this operation or \"Cancel\" to cancel and keep all locks in database.");

					alert.showAndWait();

					if(alert.getResult() == ButtonType.OK) {
						// User says ok
						Main.log("Complaint id: " + rep.getReportId() + " resolved.");
						Main.deleteReport(rep.getReportId());

						Alert alertDone = new Alert(AlertType.INFORMATION);
						alertDone.setTitle("Report Resolved");
						alertDone.setHeaderText("The report has been resolved.");

						alertDone.showAndWait();
						
						tvReport.setItems(Main.getReports());
					}
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
