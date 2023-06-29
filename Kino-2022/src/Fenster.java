import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

public class Fenster extends JFrame implements ActionListener, ItemListener {
	private JTextField kundenNrEingabe, nachnameEingabe, vornameEingabe;
	
	
	//https://toedter.com/software/
	private JDateChooser gebDatumAuswahl;
	
	private JComboBox<Film> filmAuswahl;
	private JComboBox<Vorstellung> vorstellungsAuswahl;
	private JButton reservieren;	
	
	

	private Datenbankanbindung db;

	public Fenster() {
		db = new Datenbankanbindung();

		setSize(500, 400);
		setTitle("Kinobuchungssystem");
		setLayout(new GridLayout(10, 2));

		hinzufuegen();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

	}

	private void hinzufuegen() {
		JLabel bereitsKunde = new JLabel("Bereits Kunde");
		bereitsKunde.setFont(new Font("Arial", Font.BOLD, 14));
		add(bereitsKunde);
		add(new JLabel());

		JLabel kundenNr = new JLabel("Kundennummer:");
		add(kundenNr);
		kundenNrEingabe = new JTextField();
		add(kundenNrEingabe);

		JLabel neuerKunde = new JLabel("Neuer Kunde");
		neuerKunde.setFont(new Font("Arial", Font.BOLD, 14));
		add(neuerKunde);
		add(new JLabel());		

		JLabel vorname = new JLabel("Vorname:");
		add(vorname);
		vornameEingabe = new JTextField();
		add(vornameEingabe);
		
		JLabel nachname = new JLabel("Nachname:");
		add(nachname);
		nachnameEingabe = new JTextField();
		add(nachnameEingabe);
		
		JLabel gebDatum = new JLabel("Geburtsdatum:");
		add(gebDatum);
		gebDatumAuswahl = new JDateChooser();
		add(gebDatumAuswahl);

		JLabel reservierung = new JLabel("Reservierung");
		reservierung.setFont(new Font("Arial", Font.BOLD, 14));
		add(reservierung);
		add(new JLabel());

		JLabel film = new JLabel("Film:");
		add(film);

		filmAuswahl = new JComboBox<Film>();
		Vector<Film> filme = db.filmListeGeben();
		filmAuswahl.setModel(new DefaultComboBoxModel( filme ));
		filmAuswahl.addItemListener(this);
		add(filmAuswahl);

		JLabel vorstellungen = new JLabel("Vorstellungen:");
		add(vorstellungen);
		
		//Um eine ComboBox aktualisieren zu können, muss man einen DefaultComboBoxModell-Objekt erzeugen, dem das Vector-Objekt übergeben wird
		vorstellungsAuswahl = new JComboBox<Vorstellung>();
		Vector<Vorstellung> fVorstellungen = db.vorstellungsListeGeben(((Film) filmAuswahl.getSelectedItem()).getTitel());
		vorstellungsAuswahl.setModel(new DefaultComboBoxModel(fVorstellungen));
		add(vorstellungsAuswahl);

		add(new JLabel());
		reservieren = new JButton("reservieren");
		reservieren.addActionListener(this);
		add(reservieren);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object verursacher = event.getSource();

		if (verursacher == reservieren) {
			String vorname = vornameEingabe.getText();
			String nachname = nachnameEingabe.getText();
			int idVorstellung = ( (Vorstellung) vorstellungsAuswahl.getSelectedItem() ).getIdVorstellung();
			System.out.println("idVorstellung: " + idVorstellung);
			
			int kundenNr;
			
			if (  kundenNrEingabe.getText().equals("") ) {
				if( !vorname.equals("") && !nachname.equals("") ) {					
					Calendar datum = gebDatumAuswahl.getCalendar();
					
					String gebDatum = datum.get(Calendar.YEAR) + "-" + (datum.get(Calendar.MONTH)+1) + "-" + datum.get(Calendar.DAY_OF_MONTH);
					System.out.println( gebDatum );
				
					db.kundeEinfuegen(vornameEingabe.getText(), nachnameEingabe.getText(), gebDatum);
					
					kundenNr = db.kundenNummerGeben(vorname, nachname);
					System.out.println("KundenNr: " + kundenNr);
					if (kundenNr != -1) {
						kundenNrEingabe.setText(kundenNr + "");
						System.out.println("Neuen Kunden eingefügt!");
						db.reservieren(kundenNr, idVorstellung  );
						System.out.println("Reserviert für KundenNr " + kundenNr + "!");
					}
				}			
			} else {
				//einen String in int umwandeln
				kundenNr = Integer.parseInt( kundenNrEingabe.getText() );
				boolean kundeVorhanden = db.kundeVorhanden(kundenNr);
				System.out.println("Kunde vorhanden:" + kundeVorhanden );
				if ( kundeVorhanden ) {
					db.reservieren(kundenNr, idVorstellung );
					System.out.println("Reserviert für KundenNr " + kundenNr + "!");
				}
			}
			
			//Zurücksetzen der Eingabefelder
			kundenNrEingabe.setText("");
			vornameEingabe.setText("");
			nachnameEingabe.setText("");
			//Der Kalender lässt sicht nicht so zurücksetzen, dass im Feld nichts steht. 
			//Er wird auf das aktuelle Datum zurückgesetzt.
			gebDatumAuswahl.setDate( Calendar.getInstance().getTime() );
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Object verursacher = e.getSource();

		if (verursacher == filmAuswahl && e.getStateChange() == ItemEvent.SELECTED) {
				Film selectedItem = (Film) e.getItem();
				//Ausgabe in der Konsole				
				System.out.println(selectedItem.getTitel());
				
				Vector<Vorstellung> fvorstellungen = db.vorstellungsListeGeben(selectedItem.getTitel());
				//Ausgabe in der Konsole				
				for (Vorstellung v : fvorstellungen) {
					System.out.println(v.toString());
				}
				
				vorstellungsAuswahl.setModel(new DefaultComboBoxModel(fvorstellungen));
		}

	}

	public static void main(String[] args) {
		new Fenster();
	}

}
