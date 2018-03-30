package fr.ul.miage.weather;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import fr.ul.miage.weather.view.WeatherView;

public class App extends Application {

	public static Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		App.stage = primaryStage;
		WeatherView wv = new WeatherView();
		App.stage.setResizable(false);
		App.stage.setScene(wv.getScene());
		App.stage.show();
	}

}
