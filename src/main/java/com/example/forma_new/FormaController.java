package com.example.forma_new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.DateTimeStringConverter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;

public class FormaController {
    @FXML public VBox mainBox = new VBox();
    // Textfields and Dropdowns in the GUI
    @FXML private TextField belegNummer = new TextField();
    @FXML private TextField belegDatum = new TextField();
    @FXML private TextField belegReferenz = new TextField();
    @FXML private TextField buchungsText = new TextField();
    @FXML private TextField bauadInfo = new TextField();
    @FXML private TextField betrag = new TextField();
    @FXML private TextField projNummer = new TextField();
    @FXML private TextField pdfPath = new TextField();
    @FXML private ComboBox<String> belegArt = new ComboBox<>();

    //Variables to save the input
    private String belegNummerString;
    private String belegDatumString;
    private String belegReferenzString;
    private String buchungsTextString;
    private String bauadInfoString;
    private String betragString;
    private String projNummerString;
    private String belegArtString;
    private static final String xmlPath = "src/FORMA.xml";
    static String belNr;
    static String propertiesPath = "src/data.properties";
    static Properties prop = new Properties();
    static FileInputStream fileInput;


    public FormaController() throws IOException {
        belNr = readBelNr();
    }

    @FXML
    void initialize() throws ParseException {
        ObservableList<String> data = FXCollections.observableArrayList("F", "G");
        belegNummer.setText(belNr); //sets the receipt number in the corresponding field to the current number
        belegArt.setItems(data);
        //Datumsformat setzen
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        belegDatum.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("01.01.2023")));
    }




    @FXML
    private Button pdfButton;
       @FXML
    protected void openPDF() {
           FileChooser fileChooser = new FileChooser();
           Stage stage = (Stage) mainBox.getScene().getWindow();
           //nur PDF Dateien anzeigen
           FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("pdf only", "*.pdf");
           fileChooser.getExtensionFilters().add(filter);
           File selectedFile = fileChooser.showOpenDialog(stage);
           //TODO save Filepath and name to variable, change name and move file to new path

        }



    @FXML
    protected void createXML() throws IOException {
        //Wenn keine Belegart ausgewählt wurde -> Fehlermeldung
        if(belegArt.getValue()== null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fehlendes Feld");
            alert.setHeaderText("Belegart muss angegeben werden!");
            alert.showAndWait();
            return;
        }else {
            System.out.println(readBelNr());
            readXML();
            //Alle Felder in Variablen speichern
            belegNummerString = belegNummer.getText();
            belegDatumString = belegDatum.getText();
            belegReferenzString = belegReferenz.getText();
            buchungsTextString = buchungsText.getText();
            bauadInfoString = bauadInfo.getText();
            betragString = betrag.getText();
            projNummerString = projNummer.getText();
            belegArtString = (String) belegArt.getValue();

            if (belegArtString.equals("G")) { //Betrag wird negativ wenn wir eine Gutschrift erhalten.
                betragString = String.valueOf(Integer.parseInt(betragString) * (-1));
            }
            String xmlFile = XMLCreator.xmlString(belegNummerString,belegDatumString, belegReferenzString,buchungsTextString,bauadInfoString, projNummerString,belegArtString);

            //TODO write that string to an XML File and save it in the correct location with correct name

            System.out.println(belegNummerString);
            System.out.println(belegDatumString);
            System.out.println(belegReferenzString);
            System.out.println(buchungsTextString);
            System.out.println(bauadInfoString);
            System.out.println(projNummerString);
            System.out.println(betragString);
            System.out.println(belegArtString);
        }

    }

    @FXML
    protected void close() { //should close the window. obviously.
        System.out.println("closing....");
        //TODO: Wrong Stage rn, find way to get right stage

    }

//XML METHODS

    public static void readXML() {
    }


//BELEGNUMMER METHODS

    /**
     * reads the current receipt number from the properties file. The number is incremented and saved after every receipt and is reloaded after every file
     */
    @FXML
    protected  String readBelNr() throws IOException {
        fileInput = new FileInputStream(propertiesPath);
        prop.load(fileInput);
        belNr = prop.getProperty("Belegnummer1");
        return belNr;
    }


    /**
     * sets the receipt number in the application window
     */
    @FXML
    protected void setBelNr(String belNr) {
        belegNummer.setText(belNr);
    }


    /**
     * increments the receipt number in the properties file and saves it, so the file is up to date after every new receipt.
     */
    @FXML
    protected void incBelNr(String belNr) {
        try (OutputStream output = new FileOutputStream(propertiesPath)) {
            String belNrNew = String.valueOf(Integer.parseInt(belNr) + 1);
            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("Belegnummer1", belNrNew);
            prop.setProperty("Path", "\\\\\\\\FAG-ABA-T\\\\belegout_test_forma\\\\");
            prop.setProperty("Passwort", "forma9016");
            // save properties to project root folder
            prop.store(output, null);
            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

}
