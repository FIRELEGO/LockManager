package wip;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MyButton extends Button {
	public MyButton(String name) {
		super(name);
		this.setMaxWidth(150);
		this.setMinWidth(150);
		this.setPrefWidth(150);
		this.setFont(Font.font(Main.LABEL_FONT, 14));
		this.setTextFill(Color.WHITE);
		this.setBackground(new Background(new BackgroundFill(Color.web("#003459"), CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
 