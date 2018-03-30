package fr.ul.miage.weather.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;

import java.io.InputStream;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class DialogLocationView {

	private static Dialog<Pair<String, String>> dialog = null;

	private DialogLocationView() {
		dialog = new Dialog<>();
		dialog.setTitle("Location");
		dialog.setHeaderText("Rentrer votre nouvelle location");

		// Set the icon (must be included in the project).
		InputStream input = this.getClass().getResourceAsStream("/earth.png");
		Image image = new Image(input);
		ImageView iv = new ImageView(image);
		iv.setFitHeight(50);
		iv.setFitWidth(50);
		dialog.setGraphic(iv);

		ButtonType valideButtonType = new ButtonType("Valider", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(valideButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField pays = new TextField();
		pays.setPromptText("Pays");
		TextField ville = new TextField();
		ville.setPromptText("Ville");

		grid.add(new Label("Pays:"), 0, 0);
		grid.add(pays, 1, 0);
		grid.add(new Label("Ville:"), 0, 1);
		grid.add(ville, 1, 1);

		Node valideButton = dialog.getDialogPane().lookupButton(valideButtonType);
		valideButton.setDisable(true);

		pays.textProperty().addListener((observable, oldValue, newValue) -> {
			valideButton.setDisable(newValue.trim().isEmpty() || ville.getText().isEmpty());
		});
		
		ville.textProperty().addListener((observable, oldValue, newValue) -> {
			valideButton.setDisable(newValue.trim().isEmpty() || pays.getText().isEmpty());
		});
		

		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> pays.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == valideButtonType) {
				return new Pair<>(pays.getText(), ville.getText());
			}
			return null;
		});

	}

	public static Dialog<Pair<String, String>> getDialog() {
		if(dialog == null) {
			new DialogLocationView();
		}
		return dialog;
	}

}
