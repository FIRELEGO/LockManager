package wip;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MyButton extends Button {
	public MyButton(String name) {
		super(name);
		this.setMaxWidth(150);
		this.setMinWidth(150);
		this.setPrefWidth(150);
		this.setFont(Font.font(Main.LABEL_FONT, 14));
	}
}
 