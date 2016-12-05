package wip;

import javafx.scene.control.TextField;

public class MyTextField extends TextField {
	public MyTextField(double width) {
		setMaxWidth(width);
		setMinWidth(width);
		setPrefWidth(width);
	}
}
