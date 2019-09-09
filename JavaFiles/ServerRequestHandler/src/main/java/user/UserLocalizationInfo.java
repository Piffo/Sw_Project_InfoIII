package user;
/**
 * 
 * Classe usata come struct per contenere i dati relativi alla posizione dell'utente,
 * rispetto al quale si va a calcolare la possibilitÓ di scambiare libri dell'utente stesso all'interno della rete 
 * di sharing.
 *
 * @author Paganessi Andrea - Piffari Michele - Villa Stefano
 * @version 1.0
 * @since 2018/2019
 */
public class UserLocalizationInfo {
	public double radius;
	public double lat;
	public double longit;

	public UserLocalizationInfo() {}

	public UserLocalizationInfo(double lat, double longit) {
		this.lat = lat;
		this.longit = longit;
	}

	public String toString() {
		return "Lat: " + this.lat + " Longit: " + this.longit + " Raggio d'azione: " + this.radius;
	}
}
