
public class Kinosaal {
	private int idKinosaal;
	private int anzahlPlaetze;
	
	public Kinosaal(int idKinosaal, int anzahlPlaetze) {
		super();
		this.idKinosaal = idKinosaal;
		this.anzahlPlaetze = anzahlPlaetze;
	}

	public int getIdKinosaal() {
		return idKinosaal;
	}

	public void setIdKinosaal(int idKinosaal) {
		this.idKinosaal = idKinosaal;
	}

	public int getAnzahlPlaetze() {
		return anzahlPlaetze;
	}

	public void setAnzahlPlaetze(int anzahlPlaetze) {
		this.anzahlPlaetze = anzahlPlaetze;
	}
}
