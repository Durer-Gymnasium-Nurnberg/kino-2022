import java.util.Calendar;
import java.util.GregorianCalendar;

public class Vorstellung {	
	private int idVorstellung;
	private GregorianCalendar termin;
	private Film film;
	private Kinosaal saal;
	
	public Vorstellung(int idVorstellung, GregorianCalendar termin, Film film, Kinosaal saal) {
		super();
		this.idVorstellung = idVorstellung;
		this.termin = termin;
		this.film = film;
		this.saal = saal;
	}

	public int getIdVorstellung() {
		return idVorstellung;
	}

	public void setIdVorstellung(int idVorstellung) {
		this.idVorstellung = idVorstellung;
	}

	public GregorianCalendar getTermin() {
		return termin;
	}

	public void setTermin(GregorianCalendar termin) {
		this.termin = termin;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Kinosaal getSaal() {
		return saal;
	}

	public void setSaal(Kinosaal saal) {
		this.saal = saal;
	}

	//Diese Methode ist für die Anzeige in der JComboBox nötig. Ansonsten werden nur Referenzen zu den Objekten angezeigt.
	@Override
	public String toString() {
		int wtag = termin.get(Calendar.DAY_OF_WEEK);
		String wochentag = "";
		switch(wtag) {
			case 1:	wochentag = "Sonntag";
			case 2:	wochentag = "Montag";
			case 3:	wochentag = "Dienstag";
			case 4:	wochentag = "Mittwoch";
			case 5:	wochentag = "Donnerstag";
			case 6:	wochentag = "Freitag";
			case 7:	wochentag = "Samstag";
		}
		String datum = termin.get(Calendar.DAY_OF_MONTH) + "." + termin.get(Calendar.MONTH) + "." + termin.get(Calendar.YEAR);
		String uhrzeit = termin.get(Calendar.HOUR_OF_DAY) + ":" + termin.get(Calendar.MINUTE);
		
		
		return wochentag + ", " + datum + ", " + uhrzeit + " Uhr, Kinosaal: " + saal.getIdKinosaal();		
	}
	
	
}
