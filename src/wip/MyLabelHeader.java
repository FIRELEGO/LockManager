package wip;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class MyLabelHeader extends Label {
	public MyLabelHeader(String text) {
		super(text);
		this.setFont(Font.font(Main.LABEL_FONT, 32));
	}
}
