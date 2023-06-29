import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.Vector;

public class Datenbankanbindung {
	private Connection conn;

	public Datenbankanbindung() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");		
			//conn = DriverManager.getConnection("jdbc:mysql://172.28.40.54/kino?user=sc&password=dg");	
			conn = DriverManager.getConnection("jdbc:mysql://192.168.200.2/kino2?user=sc&password=dg");	
			//conn = DriverManager.getConnection("jdbc:mysql://85.13.144.17/d03d56ab?user=d03d56ab&password=2BcyR5v32BjhzV");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Vector<Film> filmListeGeben() {
		Vector<Film> liste = new Vector<Film>();

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM film");
			while (rs.next()) {
				Film f = new Film(rs.getInt("idFilm"), rs.getString("titel"), rs.getInt("dauer"), rs.getInt("fsk"));
				liste.add(f);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return liste;
	}

	public Vector<Vorstellung> vorstellungsListeGeben() {
		Vector<Vorstellung> liste = new Vector<Vorstellung>();

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT idVorstellung, YEAR(datum) AS jahr, MONTH(datum) AS monat, DAY(datum) AS tag, HOUR(zeit) AS stunde, MINUTE(zeit) AS minute, idFilm, titel, dauer, fsk, idKinosaal, anzahlPlaetze\r\n"
					+ "FROM vorstellung v, kinosaal k, film f\r\n"
					+ "WHERE f.idFilm = v.Film_idFilm\r\n"
					+ "AND k.idKinosaal = v.Kinosaal_idKinosaal;");
			while (rs.next()) {
				GregorianCalendar termin = new GregorianCalendar(rs.getInt("jahr"), rs.getInt("monat"), rs.getInt("tag"), rs.getInt("stunde"), rs.getInt("minute") );
				//System.out.println(termin.get(Calendar.DAY_OF_MONTH) + "." + termin.get(Calendar.MONTH) + "." + termin.get(Calendar.YEAR) + "," + termin.get(Calendar.HOUR_OF_DAY) + ":" + termin.get(Calendar.MINUTE) );
				
				Film film = new Film(rs.getInt("idFilm"), rs.getString("titel"), rs.getInt("dauer"), rs.getInt("fsk"));
				Kinosaal saal = new Kinosaal(rs.getInt("idKinosaal"), rs.getInt("anzahlPlaetze") );
				
				Vorstellung v = new Vorstellung(rs.getInt("idVorstellung"), termin, film, saal);
				
				liste.add(v);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return liste;

	}
	
	public Vector<Vorstellung> vorstellungsListeGeben(String filmtitel) {
		Vector<Vorstellung> liste = new Vector<Vorstellung>();

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT idVorstellung, YEAR(datum) AS jahr, MONTH(datum) AS monat, DAY(datum) AS tag, HOUR(zeit) AS stunde, MINUTE(zeit) AS minute, idFilm, titel, dauer, fsk, idKinosaal, anzahlPlaetze\r\n"
					+ "FROM vorstellung v, kinosaal k, film f\r\n"
					+ "WHERE f.titel = '"+ filmtitel +"'\r\n"
					+ "AND f.idFilm = v.Film_idFilm\r\n"
					+ "AND k.idKinosaal = v.Kinosaal_idKinosaal;");
			while (rs.next()) {
				GregorianCalendar termin = new GregorianCalendar(rs.getInt("jahr"), rs.getInt("monat"), rs.getInt("tag"), rs.getInt("stunde"), rs.getInt("minute") );
				//System.out.println(termin.get(Calendar.DAY_OF_MONTH) + "." + termin.get(Calendar.MONTH) + "." + termin.get(Calendar.YEAR) + "," + termin.get(Calendar.HOUR_OF_DAY) + ":" + termin.get(Calendar.MINUTE) );
				
				Film film = new Film(rs.getInt("idFilm"), rs.getString("titel"), rs.getInt("dauer"), rs.getInt("fsk"));
				Kinosaal saal = new Kinosaal(rs.getInt("idKinosaal"), rs.getInt("anzahlPlaetze") );
				
				Vorstellung v = new Vorstellung(rs.getInt("idVorstellung"), termin, film, saal);
				
				liste.add(v);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return liste;

	}
	
	public void kundeEinfuegen(String vorname, String nachname, String gebDatum) {
		try {
			Statement st = conn.createStatement();	
			
			int rowCount = st.executeUpdate("INSERT INTO kunde(nachname, vorname, gebDatum) VALUES ('" + nachname + "', '" + vorname + "', '" + gebDatum + "');" );
		
			st.close ();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	public int kundenNummerGeben(String vorname, String nachname) {
		int kundenNr = -1;

		try {
			Statement st = conn.createStatement();
			//System.out.println("SELECT idKunde FROM kunde WHERE vorname='" + vorname + "' AND nachname ='" + nachname + "';");
			ResultSet rs = st.executeQuery("SELECT idKunde FROM kunde WHERE vorname='" + vorname + "' AND nachname ='" + nachname + "';");
			
			if (rs.next()) {
				kundenNr = rs.getInt("idKunde");	
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return kundenNr;		
	}
	
	public boolean kundeVorhanden(int kundenNr) {
		boolean kundeVorhanden = false;
		String vorname;
		String nachname;
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT vorname, nachname FROM kunde WHERE idKunde ='" + kundenNr + "';");
			
				
			if ( rs.next() ) {
				vorname = rs.getString("vorname");	
				nachname = rs.getString("nachname");	
				if ( !vorname.equals("") && !nachname.equals("") ) {
					kundeVorhanden = true;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return kundeVorhanden;		
	}
	
	public void reservieren(int kundenNr, int idVorstellung) {
		try {
			Statement st = conn.createStatement();	
			System.out.println("INSERT INTO reservierung(Kunde_idKunde, Vorstellung_idVorstellung) VALUES ('" + kundenNr + "', '" + idVorstellung + "');" );
			
			int rowCount = st.executeUpdate("INSERT INTO reservierung(Kunde_idKunde, Vorstellung_idVorstellung) VALUES ('" + kundenNr + "', '" + idVorstellung + "');" );
		
			st.close ();			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void verbindungBeenden() {		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Datenbankanbindung db = new Datenbankanbindung();
		Vector<Film> filme = db.filmListeGeben();
		for (Film f : filme) {
			System.out.println(f.getTitel());
		}
		
		Vector<Vorstellung> vorstellungen = db.vorstellungsListeGeben();
		for (Vorstellung v : vorstellungen) {
			System.out.println(v.getIdVorstellung());
		}
		
		Vector<Vorstellung> vorstellungenFilm = db.vorstellungsListeGeben("Robin Hood");
		for (Vorstellung v : vorstellungenFilm) {
			System.out.println(v.getIdVorstellung());
		}
		
		//db.kundeEinfuegen("Max", "Mustermann", "2000-02-09");
		
	}

}
