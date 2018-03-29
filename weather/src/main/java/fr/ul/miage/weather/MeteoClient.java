package fr.ul.miage.weather;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.ul.miage.weather.util.JSONResponse;

public class MeteoClient {

	private static final Logger LOG = Logger.getLogger(MeteoClient.class.getName());

	private final String WEBSERVICE = "http://api.wunderground.com/api/";

	private static final String API_KEY = "80816dcb4027fa4e";

	private String city;

	private String country;

	public MeteoClient() {
		this("Nancy", "France");
	}

	public MeteoClient(String city, String country) {
		this.city = city;
		this.country = country;
	}

	public JSONResponse getConditions() {
		JSONResponse res = null;
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
		res = gson.fromJson(s, JSONResponse.class);

		return res;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
