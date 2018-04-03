package fr.ul.miage.weather.view.enumeration;

public enum Vitesse {
	Kph("Kph", 0), Mph("Mph", 1);

	private String description = "";
	private int index;

	Vitesse(String description, int index) {
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
