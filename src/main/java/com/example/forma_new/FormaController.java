package com.example.forma_new;

import javafx.fxml.FXML;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.event.*;



import java.io.File;

public class FormaController {

    // Textfields and Dropdowns in the GUI
    private Stage fileWindow =new Stage();
    private FileChooser chooser ;
    public TextField belegNummer = new TextField();
    public TextField belegDatum = new TextField();
    public TextField belegReferenz = new TextField();
    public TextField buchungsText = new TextField();
    public TextField bauadInfo = new TextField();
    public TextField betrag = new TextField();
    public ComboBox projNummer = new ComboBox<>();
    public ComboBox belegArt = new ComboBox();

    //Variables to save the input
    private String belegNummerString;
    private String belegDatumString;
    private String belegReferenzString;
    private String buchungsTextString;
    private String bauadInfoString;
    private String betragString;
    private String projNummerString;
    private String belegArtString;

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
                "PDF Files", ".pdf");
        chooser.setSelectedExtensionFilter(filter);

        // Dialog zum Oeffnen von Dateien anzeigen
        File chosenPDF = chooser.showOpenDialog(fileWindow);



          //  System.out.println("Die zu öffnende Datei ist: " +
              //      chooser.getSelectedFile().getName());
            pdfButton.setDisable(false);

        }

    @FXML
    protected void createXML() {

        // TODO read data.properties, save belegnummer in variable and write new belegnummer in file
        // get values from textfields
        belegNummerString = belegNummer.getText();
        belegDatumString = belegDatum.getText();
        belegReferenzString = belegReferenz.getText();
        buchungsTextString = buchungsText.getText();
        bauadInfoString = bauadInfo.getText();
        betragString = betrag.getText();
        projNummerString = (String) projNummer.getValue();
        belegArtString = (String) belegArt.getValue();
        //TODO Open the XML Template
        // TODO change entries
        //TODO safe as new file
        //TODO make pop up that says done

    }
    }
