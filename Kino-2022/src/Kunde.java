import java.util.GregorianCalendar;

public class Kunde {	
	private int idFilm;
	private String nachname;
	private String vorname;
	private GregorianCalendar gebDatum;
	
	public Kunde(int idFilm, String nachname, String vorname, GregorianCalendar gebDatum) {
		super();
		this.idFilm = idFilm;
		this.nachname = nachname;
		this.vorname = vorname;
		this.gebDatum = gebDatum;
	}

	public int getIdFilm() {
		return idFilm;
	}

	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public GregorianCalendar getGebDatum() {
		return gebDatum;
	}

	public void setGebDatum(GregorianCalendar gebDatum) {
		this.gebDatum = gebDatum;
	}
	
	
	


}
