package com.example.forma_new;

public class XMLCreator {
    //TODO Referenz und Bauad Info an richtigen Ort
    public static String xmlString(String belegNummer, String belegDatum, String referenz, String buchungsText, String bauadInfo, String projNr, String belegArt){
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
                "        <KeyAmount>000.01</KeyAmount>\n" +
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

    public static void makeFile(String xmlString){//Makes a new XML File containing the specified Information for the receipt

    }

}
