package fr.ul.miage.weather.controler;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.ul.miage.weather.util.JSONResponse;
import fr.ul.miage.weather.view.DialogLocationView;
import fr.ul.miage.weather.view.WeatherView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Pair;

public class GeneralControler {

	private static final Logger LOG = Logger.getLogger(GeneralControler.class.getName());

	private final int TIME_THREAD = 60;

	private final String WEBSERVICE = "http://api.wunderground.com/api/";
	private final String API_KEY = "80816dcb4027fa4e";

	private int choixUniteDegre = 0;
	private int choixUniteVItesse = 0;

	private JSONResponse reponse;

	private static GeneralControler generalControler = null;

	private WeatherView weatherView;

	private String cityAct = "Nancy";

	private String countryAct = "France";

	private ScheduledExecutorService thread;

	private GeneralControler(WeatherView wv) {
		this.weatherView = wv;
		setNewLocation(countryAct, cityAct);
	}

	public static GeneralControler getControler(WeatherView wv) {
		if (generalControler == null) {
			generalControler = new GeneralControler(wv);
			generalControler.startUpdateSystem();
		}
		return generalControler;
	}

	public void stopUpdateSystem() {
		thread.shutdown();
	}

	public void startUpdateSystem() {
		thread = Executors.newScheduledThreadPool(1);
		thread.scheduleAtFixedRate(() -> {
			setNewLocation(countryAct, cityAct);
			weatherView.updateAll();
			System.out.println("okok");
		}, TIME_THREAD, TIME_THREAD, TimeUnit.SECONDS);
	}

	public JSONResponse getConditions(String country, String city) {
		String request = WEBSERVICE + API_KEY + "/conditions/q/" + country + "/" + city + ".json";
		String s = "";
		try {
			Client client = Client.create();
			WebResource r = client.resource(request.toString());
			r.accept("application/json");
			ClientResponse response = r.get(ClientResponse.class);
			if (response.getStatus() != 200) {
				LOG.severe("Erreur requête : " + request + "(code:" + response.getStatus() + ")");
				return null;
			}

			s = response.getEntity(String.class);
		} catch (Exception e) {
			LOG.severe(e.getMessage());
		}

		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		return gson.fromJson(s, JSONResponse.class);
	}

	public void changeLocationClick() {
		Optional<Pair<String, String>> result = DialogLocationView.getDialog().showAndWait();
		result.ifPresent(paysVille -> {
			verifyJSON(paysVille.getKey(), paysVille.getValue());
		});
	}

	private void verifyJSON(String pays, String ville) {
		JSONResponse json = getConditions(pays, ville);
		if (json != null && json.getCurrentObservation() != null) {
			reponse = json;
			cityAct = ville;
			countryAct = pays;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setContentText("La ville " + ville + " dans le pays " + pays + " semble introuvable.");
			alert.showAndWait();
		}
	}

	public void setNewLocation(String country, String city) {
		verifyJSON(country, city);
	}

	public String getCity() {
		return reponse.getCurrentObservation().getDisplayLocation().getCity();
	}

	public String getTemperature() {
		String res;
		if (choixUniteDegre == 0) {
			res = reponse.getCurrentObservation().getTempC() + "°C";
		} else {
			res = reponse.getCurrentObservation().getTempF() + "°F";
		}
		return res;
	}

	public String getRessenti() {
		String res;
		if (choixUniteDegre == 0) {
			res = reponse.getCurrentObservation().getFeelslikeC() + "°C";
		} else {
			res = reponse.getCurrentObservation().getFeelslikeF() + "°F";
		}
		return res;
	}

	public String getWindDir() {
		return reponse.getCurrentObservation().getWindDir();
	}

	public String getWindSpeed() {
		String res;
		if (choixUniteVItesse == 0) {
			res = reponse.getCurrentObservation().getWindKph() + " Kph";
		} else {
			res = reponse.getCurrentObservation().getWindMph() + " Mph";
		}
		return res;
	}

	public String getWheather() {
		return reponse.getCurrentObservation().getWeather();
	}

	public String getIconUrl() {
		return reponse.getCurrentObservation().getIconUrl();
	}

	public int getChoixUniteDegre() {
		return choixUniteDegre;
	}

	public void setChoixUniteDegre(int choixUniteDegre) {
		this.choixUniteDegre = choixUniteDegre;
	}

	public int getChoixUniteVItesse() {
		return choixUniteVItesse;
	}

	public void setChoixUniteVItesse(int choixUniteVItesse) {
		this.choixUniteVItesse = choixUniteVItesse;
	}

}
