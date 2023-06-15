package com.example.forma_new;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XMLCreator {
    //TODO Referenz und Bauad Info an richtigen Ort
    public static String xmlString(String belegNummer, String belegDatum, String referenz, String buchungsText, String bauadInfo, String projNr, String belegArt, String betrag){
       return "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?><AbaConnectContainer>\n" +
                "  <TaskCount>1</TaskCount>\n" +
                "  <Task>\n" +
                "    <Transaction id=\"1\">\n" +
                "      <Document mode=\"SAVE\">\n" +
                "        <AccountPayableDocumentDate>"+belegDatum+"</AccountPayableDocumentDate>\n" +
                "        <CustomDocumentDeadLine>30</CustomDocumentDeadLine>\n" +
                "        <DocumentBlockedForDisposition>false</DocumentBlockedForDisposition>\n" +
                "        <DocumentCode>"+belegArt+"</DocumentCode>\n" +
                "        <DocumentPicture>institute_0006_v2017 (2) (1) (1) (1).pdf</DocumentPicture>\n" +
                "        <DocumentStatusForDisposition>0</DocumentStatusForDisposition>\n" +
                "        <KeyAmount>"+betrag+"</KeyAmount>\n" +
                "        <LineItem mode=\"SAVE\">\n" +
                "          <Project>"+projNr+"</Project>\n" +
                "          <TaxCode>111</TaxCode>\n" +
                "          <Text>"+buchungsText+"</Text>\n" +
                "          <_USERFIELD1>Hilfstext</_USERFIELD1>\n" +
                "        </LineItem>\n" +
                "        <Note/>\n" +
                "        <Number>"+belegNummer+"</Number>\n" +
                "        <PaymentTermNumber>1</PaymentTermNumber>\n" +
                "        <ReferenceNumber>0</ReferenceNumber>\n" +
                "        <StatusIdentification>PROV1</StatusIdentification>\n" +
                "      </Document>\n" +
                "    </Transaction>\n" +
                "  </Task>\n" +
                "</AbaConnectContainer>";


    }

    /**
     * Makes a new XML File containing the specified Information for the receipt. Filename MUST contain .xml ending
     * @param xmlString String containing all the content for the XML File
     * @param outputPath path where the xml has to be put. Same path as for the PDF
     * @param filename  Must contain the file extension
     */
    public static void makeFile(String xmlString, String filename, String outputPath){
        filename = outputPath + "\\" + filename;
        try {
            File myObj = new File(filename); //TODO add path and specify Filename
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(xmlString);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
