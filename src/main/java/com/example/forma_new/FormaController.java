package com.example.forma_new;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FormaController {
    private Stage fileWindow =new Stage();
    private FileChooser chooser ;

    @FXML
    private Button pdfButton;

    @FXML
    protected void onHelloButtonClick() {
        pdfButton.setText("Welcome");
    }

    @FXML
    protected void closeProgram(){}

    @FXML
    protected void openPDF() {
        chooser = new FileChooser();
        chooser.setTitle("PDF Auswählen");
        //Nur PDF wird angezeigt und kann ausgewählt werden
       FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                "PDF Files", "pdf");
        chooser.setSelectedExtensionFilter(filter);

        // Dialog zum Oeffnen von Dateien anzeigen
        File chosenPDF = chooser.showOpenDialog(fileWindow);



          //  System.out.println("Die zu öffnende Datei ist: " +
              //      chooser.getSelectedFile().getName());
            pdfButton.setDisable(false);

        }
    }
