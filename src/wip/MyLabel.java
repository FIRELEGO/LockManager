package wip;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class MyLabel extends Label {
	public MyLabel(String text) {
		super(text);
		this.setFont(Font.font(Main.LABEL_FONT, 14));
	}
}
