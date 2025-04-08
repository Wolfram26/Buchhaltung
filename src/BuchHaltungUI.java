import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BuchHaltungUI {
    private JTextField txt_betrag;
    private JTextField txt_datum;
    private JComboBox cbx_kategorie;
    private JTextField txt_beschreibung;
    private JLabel lbl_betrag;
    private JLabel lbl_kategorie;
    private JLabel lbl_datum;
    private JLabel lbl_beschreibung;
    private JButton btn_anzeigen;
    private JButton btn_speichern;
    private JPanel Buchhaltung_Panel;

    private Connection conn;

    public BuchHaltungUI() {
        // Kategorien ComboBox
        cbx_kategorie.addItem("Gehalt");
        cbx_kategorie.addItem("Geschenke");
        cbx_kategorie.addItem("Lebensmittel");
        cbx_kategorie.addItem("Miete");
        cbx_kategorie.addItem("Freizeit");
        cbx_kategorie.addItem("Sonstiges");

        // DB-Verbindung
        connectToDatabase();

        // Speichern Button Listener
        btn_speichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEntry();
            }
        });

        // Anzeigen Button Listener
        btn_anzeigen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEntries();
            }
        });
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/HaushaltsApp?useSSL = false&allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "1234";

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Verbindung erfolgreich!");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Verbindung zur Datenbank hat nicht funktioniert!");
        }
    }

    private void saveEntry() {
        String betragStr = txt_betrag.getText();
        String kategorie = (String) cbx_kategorie.getSelectedItem();
        String datum = txt_datum.getText();
        String beschreibung = txt_beschreibung.getText();

        if (betragStr.isEmpty() || datum.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bitte Betrag und Datum eingeben!");
            return;
        }

        try {
            double betrag = Double.parseDouble(betragStr);

            String sql = "INSERT INTO Einnahmen (betrag, kategorie, datum, beschreibung) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, betrag);
            pstmt.setString(2, kategorie);
            pstmt.setString(3, datum);
            pstmt.setString(4, beschreibung);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Eintrag gespeichert!");

            // Felder leer
            txt_betrag.setText("");
            txt_datum.setText("");
            txt_beschreibung.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ungültiger Betrag!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fehler beim Speichern!");
        }
    }

    private void showEntries() {
        StringBuilder output = new StringBuilder();

        try {
            String sql = "SELECT kategorie, betrag, datum, beschreibung FROM Einnahmen ORDER BY kategorie, datum";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            String lastKategorie = "";
            while (rs.next()) {
                String kategorie = rs.getString("kategorie");
                double betrag = rs.getDouble("betrag");
                String datum = rs.getString("datum");
                String beschreibung = rs.getString("beschreibung");

                if (!kategorie.equals(lastKategorie)) {
                    output.append("\nKategorie: ").append(kategorie).append("\n");
                    output.append("-------------------------------------\n");
                    lastKategorie = kategorie;
                }

                output.append("Datum: ").append(datum).append(" | Betrag: ").append(betrag)
                        .append(" | ").append(beschreibung).append("\n");
            }

            if (output.length() == 0) {
                output.append("Keine Einträge gefunden.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            output.append("Fehler beim Laden der Daten.");
        }

        // Neues Fenster
        JTextArea textArea = new JTextArea(output.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame frame = new JFrame("Alle Einträge");
        frame.setSize(500, 400);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getBuchhaltungPanel() {
        return Buchhaltung_Panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Buchhaltung");
        frame.setContentPane(new BuchHaltungUI().Buchhaltung_Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
