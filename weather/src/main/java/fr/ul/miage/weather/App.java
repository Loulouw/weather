package fr.ul.miage.weather;

import java.util.logging.Logger;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import fr.ul.miage.weather.view.WeatherView;

/**
 * Hello world!
 *
 */
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
		App.stage.initStyle(StageStyle.UNIFIED);
		App.stage.setScene(wv.getScene());
		App.stage.show();
	}
	
	/*public static void main(String[] args) {
		String ville = "Nancy";
		String pays = "France";

		Options options = new Options();

		Option villeO = new Option("v", "Ville", true, "Ville");
		options.addOption(villeO);

		Option paysO = new Option("p", "Pays", true, "Pays");
		options.addOption(villeO);

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(options, args);
			if (line.hasOption("v")) {
				ville = line.getOptionValue("v");
			}

			if (line.hasOption("p")) {
				pays = line.getOptionValue("p");
			}
		} catch (ParseException exp) {
			LOG.severe("Erreur dans la ligne de commande");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("App", options);
			System.exit(1);
		}
		
		MeteoClient cl = new MeteoClient(ville,pays);
		Selection select = new Selection(cl.getConditions());
		
		System.out.println(select.getCity() + " " + select.getTemperature() + " " + select.getWheather() + " " + select.getIconUrl());

	}*/
}
