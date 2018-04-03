package fr.ul.miage.weather.view;

import fr.ul.miage.weather.controler.GeneralControler;
import fr.ul.miage.weather.view.enumeration.UniteDegre;
import fr.ul.miage.weather.view.enumeration.Vitesse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OptionsView {

	private GeneralControler generalControler;
	private BorderPane root;
	private Stage stage;
	private Scene scene;
	private WeatherView weatherView;

	private static OptionsView optionsView = null;

	private OptionsView(WeatherView weatherView) {
		this.weatherView = weatherView;
		generalControler = GeneralControler.getControler(weatherView);

		root = new BorderPane();
		root.setStyle(Style.ROOT);

		scene = new Scene(root, 300, 200, Color.WHITE);

		root.setTop(getPaneTop());
		root.setCenter(getPaneMiddle());

		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
	}

	public static OptionsView getOptionsView(WeatherView weatherView) {
		if (optionsView == null) {
			optionsView = new OptionsView(weatherView);
		}

		return optionsView;
	}

	private Pane getPaneTop() {
		BorderPane top = new BorderPane();
		Text titreOption = new Text("Options");
		titreOption.setFont(new Font("Verdana", 20));
		top.setCenter(titreOption);
		return top;
	}

	private Pane getPaneMiddle() {
		GridPane middle = new GridPane();
		middle.setPadding(new Insets(15, 5, 5, 5));

		Text labelChoixUniteDegre = new Text("Choix unité degré : ");
		labelChoixUniteDegre.setFont(new Font("Verdana", 15));
		ObservableList<UniteDegre> choixDegre = FXCollections.observableArrayList(UniteDegre.values());
		final ComboBox<UniteDegre> choixUniteDegre = new ComboBox<UniteDegre>(choixDegre);
		choixUniteDegre.setEditable(false);
		choixUniteDegre.getSelectionModel().select(generalControler.getChoixUniteDegre());

		choixUniteDegre.setOnAction(event -> {
			generalControler.setChoixUniteDegre(choixUniteDegre.getValue().getIndex());
			weatherView.updateAll();
		});

		Text labelChoixUniteVitesse = new Text("Choix unité degré : ");
		labelChoixUniteVitesse.setFont(new Font("Verdana", 15));
		ObservableList<Vitesse> choixVitesse = FXCollections.observableArrayList(Vitesse.values());
		final ComboBox<Vitesse> choixUniteVitesse = new ComboBox<Vitesse>(choixVitesse);
		choixUniteVitesse.setEditable(false);
		choixUniteVitesse.getSelectionModel().select(generalControler.getChoixUniteDegre());

		choixUniteVitesse.setOnAction(event -> {
			generalControler.setChoixUniteVItesse(choixUniteVitesse.getValue().getIndex());
			weatherView.updateAll();
		});

		middle.add(labelChoixUniteDegre, 0, 0);
		middle.add(choixUniteDegre, 1, 0);
		middle.add(labelChoixUniteVitesse, 0, 1);
		middle.add(choixUniteVitesse, 1, 1);

		return middle;
	}

	public Stage getStage() {
		return stage;
	}

}
