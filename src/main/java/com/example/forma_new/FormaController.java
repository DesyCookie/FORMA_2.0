package com.example.forma_new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import java.io.File;
import java.io.IOException;

public class FormaController {
    @FXML public VBox mainBox = new VBox();
    // Textfields and Dropdowns in the GUI
    @FXML private TextField belegNummer = new TextField();
    @FXML private TextField belegDatum = new TextField();
    @FXML private TextField belegReferenz = new TextField();
    @FXML private TextField betrag = new TextField();
    @FXML private TextField buchungsText = new TextField();
    @FXML private TextField projNummer = new TextField();
    @FXML private TextField bauadInfo = new TextField();
    @FXML private TextField pdfPath = new TextField();
    @FXML private TextField exportPathField = new TextField();
    @FXML private ComboBox<String> belegArt = new ComboBox<>();
    @FXML private Button exportButton = new Button();
    @FXML private Button pdfButton = new Button();

    //Variables to save the input
    private String belegNummerString;
    private String belegDatumString;
    private String belegReferenzString;
    private String buchungsTextString;
    private String bauadInfoString;
    private String betragString;
    private String projNummerString;
    private String belegArtString;
    private String pdfName;

    private String pdfNewName;
    private String pdfSource; //Ursprungsort PDF Datei
    private String pdfSourcePath;

    private String destination; //destination path for PDF copy and generated XML
    static String belNr;
    static String propertiesPath = "src/data.properties";
    static Properties prop = new Properties();
    static FileInputStream fileInput;
    static String xmlName;

    static boolean testmode = true;




    public FormaController() throws IOException {
        belNr = readBelNr();

    }

    @FXML
    void initialize() throws ParseException, IOException {
        ObservableList<String> data = FXCollections.observableArrayList("F", "G");
        belegNummer.setText(belNr); //sets the receipt number in the corresponding field to the current number
        incBelNr(readBelNr()); //belegnummer im file direkt erhöhen damit keine nummer doppelt vergeben wird
        belegArt.setItems(data);
        //Datumsformat setzen
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        belegDatum.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("01.01.2023")));
        exportPathField.setText(getOutPath());
        destination = getOutPath();
        if(testmode){
            belegDatum.setText("01.01.2023");
            belegReferenz.setText("testref");
            betrag.setText("1337");
            buchungsText.setText("Testtext");
            projNummer.setText("9999");
            bauadInfo.setText("bauad TEST");
        }

    }





       @FXML
    protected void openPDF() {
           FileChooser fileChooser = new FileChooser();
           Stage stage = (Stage) mainBox.getScene().getWindow();
           //nur PDF Dateien anzeigen
           FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("pdf only", "*.pdf");
           fileChooser.getExtensionFilters().add(filter);
           File selectedFile = fileChooser.showOpenDialog(stage);
           if(selectedFile == null){return;}
           pdfPath.setText(selectedFile.getPath());
           pdfSource = pdfPath.getText();
           pdfSourcePath = selectedFile.getPath();
           pdfName = selectedFile.getName();
           //PDF will be Copied when XML Button is pressed
        }



    @FXML
    protected void createXML() throws IOException {
        readFields();
        copyPDF(pdfSourcePath, renamePDF(pdfSourcePath, belegNummerString) );
        xmlName = "FORMA_"+belegNummerString+".xml"; //Name für XML mit Belegnummer
        String xmlFile = XMLCreator.xmlString(belegNummerString,belegDatumString, belegReferenzString,buchungsTextString,bauadInfoString, projNummerString,belegArtString,betragString,pdfNewName);
        XMLCreator.makeFile(xmlFile, xmlName, destination);

        //Alle Felder in Variablen speichern
        incBelNr(belegNummerString); //belegnummer in file erhöhen
        belegNummer.setText(readBelNr());//neue Belegnummer aus file setzen
        //Felder resetten
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("XML Erstellt");
        alert.setHeaderText("Das XML wurde erfolgreich erstellt!");
        alert.showAndWait();
        belegDatum.setText("01.01.2023");
        belegReferenz.setText("");
        betrag.setText("");
        buchungsText.setText("");
        projNummer.setText("");
        bauadInfo.setText("");
        pdfPath.setText("");

        }



    @FXML
    protected void close() { //should close the window. obviously.
        System.out.println("closing....");
        //TODO: Wrong Stage rn, find way to get right stage

    }


    @FXML
    protected void readFields(){
        //Wenn keine Belegart ausgewählt wurde -> Fehlermeldung
        if(belegArt.getValue()== null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fehlendes Feld");
            alert.setHeaderText("Belegart muss angegeben werden!");
            alert.showAndWait();
            return;
        }else {

            belegNummerString = belegNummer.getText();
            belegDatumString = belegDatum.getText();
            belegReferenzString = belegReferenz.getText();
            buchungsTextString = buchungsText.getText();
            bauadInfoString = bauadInfo.getText();
            betragString = betrag.getText();
            projNummerString = projNummer.getText();
            belegArtString = (String) belegArt.getValue();

            if (belegArtString.equals("G")) { //Betrag wird negativ wenn wir eine Gutschrift erhalten.
                if (betragString.equals("")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Fehlendes Feld");
                    alert.setHeaderText("Betrag muss angegeben werden!");
                    alert.showAndWait();
                    return;
                } else {
                    betragString = String.valueOf(Integer.parseInt(betragString) * (-1));
                }
            }
        }
    }

//BELEGNUMMER METHODS

    /**
     * reads the current receipt number from the properties file. The number is incremented and saved after every receipt and is reloaded after every file
     */
    @FXML
    protected  String readBelNr() throws IOException {
        fileInput = new FileInputStream(propertiesPath);
        prop.load(fileInput);
        belNr = prop.getProperty("Belegnummer");
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
            String belNrNew = String.valueOf((Integer.parseInt(belNr) + 1));
            // set the properties value
            prop.replace("Belegnummer", belNrNew);
            // save properties to project root folder
            prop.store(output, null);
            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
//OUTPUT PATH METHODS
    /**
     *
     */
    @FXML //read output path from file
    protected String getOutPath() throws IOException {
        fileInput = new FileInputStream(propertiesPath);
        prop.load(fileInput);
        return prop.getProperty("OutPath");

    }

    protected void setOutPath(String outPath){
        try (OutputStream output = new FileOutputStream(propertiesPath)) {

            prop.replace("OutPath",outPath );

            // save properties to project root folder
            prop.store(output, null);
            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    /**
     * PDF RENAME AND MOVE
     */

    public String renamePDF (String pdfSourcePath, String belegNummerString){ //new Name for PDF "name.pdf" -> "name_BELEGNUMMER.pdf"

        pdfNewName=  pdfSourcePath.replace(".pdf", "_"+belegNummerString+".pdf");
        return pdfName;
    }

    public void copyPDF(String origin, String destination) throws IOException {
        Path originPath = Path.of(origin);
        Path destinationPath = Path.of(destination);
        Files.copy(originPath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
//TODO Outputpfad fürs PDF isch falsch, NewName + Path usem Prop file wär richtig
