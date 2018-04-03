package fr.ul.miage.weather.view.enumeration;

public enum UniteDegre{
	Celsius("Celsius (°C)",0), Fahrenheit("Fahrenheit (°F)",1);

	private String description = "";
	private int index;
	
	UniteDegre(String description,int index) {
		this.description = description;
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public String toString() {
		return description;
	}
}
