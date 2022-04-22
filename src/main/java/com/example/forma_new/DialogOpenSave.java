/**package com.example.forma_new;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

// In dieser Klasse wird die Datei ausgewählt, umbenannt und abgespeichert
public class DialogOpenSave {

    JFileChooser chooser = new JFileChooser();
    public String newPDFName = "";

    //Hier wird die pdf Datei geöffnet, wenn auf xml Button geklickt wird
    public void open_change_file_Dialog(JButton XMLButton, JPanel panel) {

        //Nur PDF wird angezeigt und kann ausgewählt werden
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PDF Files", "pdf");
        chooser.setFileFilter(filter);

        // Dialog zum Oeffnen von Dateien anzeigen
        int rueckgabeWert = chooser.showDialog(panel, "Dateiauswahl PDF");

        // Abfrage, ob auf "Öffnen" geklickt wurde
        if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            // Ausgabe der ausgewaehlten Datei
            System.out.println("Die zu öffnende Datei ist: " +
                    chooser.getSelectedFile().getName());
            XMLButton.setEnabled(true);
        }
    }

    //Der Dateiname wird überschrieben
    public void rename_file_save_Dialog(String path, String belegNumberNew) {

        try {
            File selectedFile = chooser.getSelectedFile();
            if (selectedFile == null) {
                return;
            }

            String sourceFileName = selectedFile.getName();
            if (!sourceFileName.contains("_")) {
                String test = sourceFileName.replace(".pdf", "_" + belegNumberNew + ".pdf");

                // eretze den string .pdf mit nummer.pdf -> bspl. abcdf.pdf -> abcdf9999099.pdf
                newPDFName = test;
            } else {
                String[] parts = sourceFileName.split("_");
                parts[1] = belegNumberNew;

                for (int i = 0; i < parts.length; i++) {
                    newPDFName = newPDFName + parts[i];

                    if (parts[i] != parts[parts.length - 1])
                        newPDFName = newPDFName + "_";
                }

                if (newPDFName.endsWith("pdf")) {
                    System.out.println("mache nichts");

                } else {
                    newPDFName = newPDFName + ".pdf";

                }
            }

            String pathEnd = path.substring(path.length() - 1);
            String slash = "\\";

            if (!pathEnd.equals(slash)) {
                path = path + slash;
            }


            Path target = Paths.get(path + newPDFName);

            setNewPDFName(newPDFName);

            Path source = Paths.get(selectedFile.getAbsolutePath());
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Der Pfad konnte nicht gefunden werden!");
        }
    }

    //Ein getter Methode und ein setter Methode.
    //Gether Methode: holt den Namen der PDF
    //Setter Methode: Setzt den Namen der PDF
    public String getNewPDFName() {
        return newPDFName;
    }
    public void setNewPDFName(String newPDFName) {
        this.newPDFName = newPDFName;
    }
}
*/