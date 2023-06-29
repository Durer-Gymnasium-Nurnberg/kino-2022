
public class Reservierung {
	private int idReservierung;
	private Vorstellung vorst;
	private Kunde kunde;
	
	public Reservierung(int idReservierung, Vorstellung vorst, Kunde kunde) {
		super();
		this.idReservierung = idReservierung;
		this.vorst = vorst;
		this.kunde = kunde;
	}

	public int getIdReservierung() {
		return idReservierung;
	}
	
	public void setIdReservierung(int idReservierung) {
		this.idReservierung = idReservierung;
	}
	
	public Vorstellung getVorst() {
		return vorst;
	}
	
	public void setVorst(Vorstellung vorst) {
		this.vorst = vorst;
	}
	
	public Kunde getKunde() {
		return kunde;
	}
	
	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
}
