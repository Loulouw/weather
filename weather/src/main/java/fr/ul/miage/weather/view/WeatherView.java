package fr.ul.miage.weather.view;

import java.io.InputStream;

import fr.ul.miage.weather.controler.GeneralControler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WeatherView {

	private Scene scene;
	private GeneralControler generalControler;

	private BorderPane root;

	public WeatherView() {
		generalControler = new GeneralControler();

		root = new BorderPane();
		root.setStyle(Style.ROOT);

		scene = new Scene(root, 400, 500, Color.WHITE);

		root.setTop(getPaneTop());
		root.setCenter(getPaneCenter());
		root.setBottom(getPaneBottom());
	}

	private ImageView getIcon(String resourcePath) {
		InputStream input = this.getClass().getResourceAsStream(resourcePath);
		Image image = new Image(input);
		ImageView iv = new ImageView(image);
		iv.setFitHeight(20);
		iv.setFitWidth(20);
		return iv;
	}

	public Pane getPaneTop() {
		BorderPane top = new BorderPane();
		top.setPadding(new Insets(10, 10, 10, 10));

		// Left part
		MenuItem localisation = new MenuItem("Localisation", this.getIcon("/earth.png"));
		MenuButton menuButton = new MenuButton("", this.getIcon("/menu.png"), localisation);

		top.setLeft(menuButton);

		// Center Part
		Text titre = new Text("Météo " + generalControler.getCity());
		titre.setFont(new Font("Verdana", 20));

		top.setCenter(titre);

		// Rigth Part
		Text blank = new Text("                 ");

		top.setRight(blank);

		return top;
	}

	public Pane getPaneCenter() {
		GridPane center = new GridPane();
		center.setAlignment(Pos.CENTER);
		
		
		Text statut = new Text(generalControler.getWheather());
		statut.setFont(new Font("Verdana",18));
		Text temperature = new Text(generalControler.getTemperatureC() + "°C" + " / " + generalControler.getTemperatureF() + "°F");
		temperature.setFont(new Font("Verdana",18));
		
		Image img = new Image(generalControler.getIconUrl());
		ImageView icon = new ImageView(img);
		icon.setFitHeight(100);
		icon.setFitWidth(100);
		
		center.add(statut, 1, 0);
		center.add(temperature,1,1);
		center.add(icon, 1, 2);
		
		return center;
	}

	public Pane getPaneBottom() {
		return new Pane();
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

}
