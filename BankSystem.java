// Importierte Bibliotheken  
import java.util.ArrayList; // für dynamische Liste
import java.util.Scanner; //für Benutzereingabe

// Bankkonto mit verschiedene Eigenschaften
class Konto {
    protected String kontoinhaber;
    protected String bankleitzahl;
    protected String kontonummer;
    protected double kontostand;
    protected double ueberziehungsrahmen;
    protected double kontofuehrungsgebuehren;
    protected String kontoart;

    // Konstruktor (Kontostand standartmäßig auf 0)
    public Konto(String kontoinhaber, String bankleitzahl, String kontonummer, String kontoart) {
        this.kontoinhaber = kontoinhaber;
        this.bankleitzahl = bankleitzahl;
        this.kontonummer = kontonummer;
        this.kontostand = 0;
        this.kontoart = kontoart;
    }

    // Methode für Geld einzahlen
    public void einzahlen(double betrag) {
        this.kontostand += betrag;
        System.out.println("Einzahlung erfolgreich. Neuer Kontostand: " + this.kontostand);
    }

    // Methode für Geld abheben (mit überprüfung des Überziehungsrahmen und Fehlermeldung)
    public void abheben(double betrag) {
        if (this.kontostand - betrag >= -this.ueberziehungsrahmen) {
            this.kontostand -= betrag;
            System.out.println("Abhebung erfolgreich. Neuer Kontostand: " + this.kontostand);
        } else {
            System.out.println("Abhebung nicht möglich. Überziehungsrahmen überschritten!");
        }
    }

    // Kontoauszug ausgeben
    public void kontoauszug() {
        System.out.println("------------------------------------------------");
        System.out.println("Kontoauszug");
        System.out.println("Kontoinhaber: " + kontoinhaber);
        System.out.println("BLZ: " + bankleitzahl);
        System.out.println("Kontonummer: " + kontonummer);
        System.out.println("Kontostand: " + kontostand);
        System.out.println("------------------------------------------------");
    }
}

// Klasse verwaltet Konten und steuert Interaktion
public class BankSystem {
    //ArrayList die alle erstellten Konten speichert
    private static ArrayList<Konto> konten = new ArrayList<>();
    // Für Interaktion/Benutzereingabe
    private static Scanner scanner = new Scanner(System.in);

    // Menüführung
    public static void main(String[] args) {
        while (true) {
            System.out.println("Welche Aktion möchten Sie durchführen?");
            System.out.println("1 - Konto anlegen");
            System.out.println("2 - Einzahlen");
            System.out.println("3 - Abheben");
            System.out.println("4 - Kontoauszug");
            System.out.println("5 - Konto auflösen");
            System.out.println("0 - Programm beenden");
            int auswahl = scanner.nextInt();
            scanner.nextLine();

            switch (auswahl) {
                case 1:
                    kontoAnlegen();
                    break;
                case 2:
                    einzahlen();
                    break;
                case 3:
                    abheben();
                    break;
                case 4:
                    kontoauszug();
                    break;
                case 5:
                    kontoAufloesen();
                    break;
                case 0:
                    System.out.println("Programm wird beendet.");
                    return;
                default:
                    System.out.println("Ungültige Eingabe.");
            }
        }
    }

    // Neues Konto erstellen 
    private static void kontoAnlegen() {
        System.out.print("Kontoinhaber: ");
        String inhaber = scanner.nextLine();
        System.out.print("Bankleitzahl: ");
        String blz = scanner.nextLine();
        System.out.print("Kontonummer: ");
        String knr = scanner.nextLine();
        System.out.print("Kontoart (Girokonto, Sparkonto, Kreditkonto): ");
        String art = scanner.nextLine();
        
        // Erstellt neues Konto
        Konto neuesKonto = new Konto(inhaber, blz, knr, art);
        konten.add(neuesKonto);
        System.out.println("Konto erfolgreich angelegt.");
    }

    // Durchsucht ob Konto vorhanden (für Methode einzahlen() und auszahlen()) 
    private static Konto kontoSuchen() {
        System.out.print("Geben Sie die Kontonummer ein: ");
        String knr = scanner.nextLine();
        for (Konto k : konten) {
            if (k.kontonummer.equals(knr)) {
                return k;
            }
        }
        System.out.println("Konto nicht gefunden.");
        return null;
    }

    // Ruft kontoSuchen() auf. Wenn Konto exisistiert, einzahlen möglich
    private static void einzahlen() {
        Konto k = kontoSuchen();
        if (k != null) {
            System.out.print("Betrag zum Einzahlen: ");
            double betrag = scanner.nextDouble();
            k.einzahlen(betrag);
        }
    }

    // Ruft kontoSuchen() auf. Wenn Konto exisistiert, abheben möglich
    private static void abheben() {
        Konto k = kontoSuchen();
        if (k != null) {
            System.out.print("Betrag zum Abheben: ");
            double betrag = scanner.nextDouble();
            k.abheben(betrag);
        }
    }

    // Gibt Kontoinfo aus (Methode der Konto Klasse)
    private static void kontoauszug() {
        Konto k = kontoSuchen();
        if (k != null) {
            k.kontoauszug();
        }
    }

    // Entfernt Konto aus Array
    private static void kontoAufloesen() {
        Konto k = kontoSuchen();
        if (k != null) {
            konten.remove(k);
            System.out.println("Konto erfolgreich aufgelöst.");
        }
    }
}
