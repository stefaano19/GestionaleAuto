package com.example.gestionaleauto.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 */
public class QueryToXML {

    public Connection connection(String query, String xmlFilePath){

        try {
            // Configura la connessione al tuo database
            String url = "jdbc:postgresql://localhost:5432/gestionaleAuto";
            String username = "postgres";
            String password = "Stefanobru01";

            // Crea la connessione al database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Esegui la query e genera il documento XML
            Document xmlDocument = executeQueryToXML(connection, query);

            // Salva il documento XML nella cartella specificata
            saveXMLDocument(xmlDocument, xmlFilePath);

            System.out.println("Risultato della query salvato in formato XML con successo.");

            // Chiudi la connessione al database
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Document executeQueryToXML(Connection connection, String query)
            throws ParserConfigurationException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        try {
            // Esegui la query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Crea l'elemento radice
            Element rootElement = doc.createElement("results");
            doc.appendChild(rootElement);

            // Aggiungi i risultati della query come elementi figli
            while (resultSet.next()) {
                Element resultElement = doc.createElement("result");

                // Aggiungi gli elementi del risultato come sottoelementi
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Element fieldElement = doc.createElement(columnName);
                    fieldElement.appendChild(doc.createTextNode(resultSet.getString(i)));
                    resultElement.appendChild(fieldElement);
                }

                rootElement.appendChild(resultElement);
            }

            // Chiudi il result set e lo statement
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doc;
    }

    public static void saveXMLDocument(Document doc, String filePath)
            throws TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        OutputStream outputStream = new FileOutputStream(file);
        StreamResult result = new StreamResult(outputStream);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);

        outputStream.close();
    }
}