package fr.ul.miage.weather.controler;

import java.util.Optional;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.ul.miage.weather.util.JSONResponse;
import fr.ul.miage.weather.view.DialogLocationView;
import javafx.scene.control.Dialog;
import javafx.util.Pair;

public class GeneralControler {

	private static final Logger LOG = Logger.getLogger(GeneralControler.class.getName());

	private final String WEBSERVICE = "http://api.wunderground.com/api/";
	private final String API_KEY = "80816dcb4027fa4e";

	private JSONResponse reponse;

	public GeneralControler() {
		setNewLocation("France", "Nancy");
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
			JSONResponse json = getConditions(paysVille.getKey(), paysVille.getValue());
			if (json != null) {
				reponse = json;
			}
		});
	}

	private String testNull(String entree) {
		if (entree == null) {
			return "ND";
		}
		return entree;
	}

	public void setNewLocation(String country, String city) {
		reponse = getConditions(country, city);
	}

	public String getCity() {
		return testNull(reponse.getCurrentObservation().getDisplayLocation().getCity());
	}

	public String getTemperatureC() {
		return reponse.getCurrentObservation().getTempC() + "";
	}

	public String getTemperatureF() {
		return reponse.getCurrentObservation().getTempF() + "";
	}

	public String getWheather() {
		return testNull(reponse.getCurrentObservation().getWeather());
	}

	public String getIconUrl() {
		return testNull(reponse.getCurrentObservation().getIconUrl());
	}

}
