
public class Film {	
	private int idFilm;
	private String titel;
	private int dauer;
	private int fsk;
	
	public Film(int idFilm, String titel, int dauer, int fsk) {
		super();
		this.idFilm = idFilm;
		this.titel = titel;
		this.dauer = dauer;
		this.fsk = fsk;
	}
	
	public int getIdFilm() {
		return idFilm;
	}
	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public int getDauer() {
		return dauer;
	}
	public void setDauer(int dauer) {
		this.dauer = dauer;
	}
	public int getFsk() {
		return fsk;
	}
	public void setFsk(int fsk) {
		this.fsk = fsk;
	}

	//Diese Methode ist für die Anzeige in der JComboBox nötig. Ansonsten werden nur Referenzen zu den Objekten angezeigt.
	@Override
	public String toString() {
		return titel + ", " + dauer + " Minuten, FSK: " + fsk;
	}	
	
}
