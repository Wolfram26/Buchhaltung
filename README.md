# Buchhaltungs-App mit Java & MySQL

## Informationen

- **Name:** Teodor Stanculeasa
- **Fach:** ITL_12
- **Auftrag:** 9. Aufgabenstellung: Buchhaltung
- **Datum:** 08.04.2025

## Projektbeschreibung

Die Anwendung ermöglicht es, Einnahmen in eine MySQL-Datenbank zu speichern und über eine grafische Oberfläche (Swing GUI) anzuzeigen. Es wurde mit IntelliJ entwickelt und nutzt JDBC für die Datenbankverbindung.

## Verwendete Technologien

- Java (Swing GUI)
- JDBC Connector
- MySQL / XAMPP
- IntelliJ IDEA

## Setup

### 1. XAMPP starten
Der MySQL-Server wurde über XAMPP gestartet:
![XAMPP Starten](images/starting_xampp.png)

### 2. Tabellenstruktur (SQL)
Die Tabelle `Einnahmen` wurde mit folgendem SQL-Code erstellt:
```sql
CREATE TABLE Einnahmen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    betrag DOUBLE NOT NULL,
    kategorie VARCHAR(50),
    datum DATE,
    beschreibung TEXT
);
```
![SQL Code](images/sql_code.png)

### 3. Neue Datenbank erstellen
Die Datenbank `HaushaltsApp` wurde verbunden:
![Datenbank erstellen](images/adding_new_db.png)

### 4. Verbindung testen
Die Verbindung wurde erfolgreich aufgebaut mit JDBC:
![Verbindung erfolgreich](images/connection_successful.png)

### 5. Java Connector einbinden
- Speicherort: `C:\Users\z004tysp\Documents\Schule\Jahr_2\ITL_12_KPH\Buchhaltung`
- JAR-Datei im Projekt als Bibliothek hinzugefügt

## GUI & Funktionen

### Benutzeroberfläche
Die grafische Oberfläche wurde mit Swing erstellt. Sie erlaubt das Eintragen von Einnahmen mit Betrag, Datum, Kategorie und Beschreibung.
![Benutzeroberfläche](images/ui.png)

### Datenspeicherung
Einträge werden per JDBC in die Datenbank geschrieben. Pflichtfelder sind Betrag und Datum.

### Datenanzeige
Beim Klicken auf "Anzeigen" wird ein neues Fenster mit allen gespeicherten Daten geöffnet:
![Daten anzeigen](images/anzeigen.png)

## Projektstruktur

- **Java Code:** `\Buchhaltung\src\BuchhaltungUI.java`
- **JDBC Connector:** Im Hauptverzeichnis eingebunden
- **Bilder:** `images/` Ordner im Projektverzeichnis

## Fazit

Die Anwendung demonstriert erfolgreich eine einfache Java-Datenbankverbindung mit Benutzeroberfläche. Erweiterungen wie Ausgabenverwaltung, Diagramme oder CSV-Export sind einfach umsetzbar.
