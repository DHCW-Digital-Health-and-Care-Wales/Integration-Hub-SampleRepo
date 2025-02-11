package org.example;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v251.message.ADT_A01;
import ca.uhn.hl7v2.model.v251.segment.PID;
import ca.uhn.hl7v2.parser.PipeParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;

public class XmlToHl7Converter {

    private static final String XML_FILE = "example.xml";
    private static final String SCHEMA_FILE = "WPAS_Schema.xsd";

    public static void main(String[] args) {
        try {
            // Load XML file
            InputStream xmlFile = XmlToHl7Converter.class.getClassLoader().getResourceAsStream(XML_FILE);
            if (xmlFile == null) {
                throw new IllegalArgumentException("XML file not found!");
            }

            // Load XSD file
            InputStream xsdFile = XmlToHl7Converter.class.getClassLoader().getResourceAsStream(SCHEMA_FILE);
            if (xsdFile == null) {
                throw new IllegalArgumentException("XSD file not found!");
            }

            // Validate XML against XSD
            if (!validateXMLSchema(xsdFile, xmlFile)) {
                System.out.println("XML validation failed.");
                return;
            }
            System.out.println("XML is valid.");

            // Reset input stream for parsing after validation
            xmlFile = XmlToHl7Converter.class.getClassLoader().getResourceAsStream(XML_FILE);


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Create an HL7 ADT_A01 message
            ADT_A01 adt = new ADT_A01();
            adt.initQuickstart("ADT", "A01", "P");

            // Populate the PID segment with data from the XML
            PID pid = adt.getPID();
            NodeList nodeList = doc.getElementsByTagName("TRANSACTION");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    pid.getPatientID().getIDNumber().setValue(getTagValue("NHS_NUMBER", element));
                    pid.getPatientName(0).getFamilyName().getSurname().setValue(getTagValue("SURNAME", element));
                    pid.getPatientName(0).getGivenName().setValue(getTagValue("FORENAME", element));
                    pid.getDateTimeOfBirth().getTime().setValue(getTagValue("BIRTHDATE", element));
                    pid.getAdministrativeSex().setValue(getTagValue("SEX", element));
                    pid.getPatientAddress(0).getStreetAddress().getStreetName().setValue(getTagValue("ADDRESS_1", element));
                    pid.getPatientAddress(0).getZipOrPostalCode().setValue(getTagValue("POSTCODE", element));
                    pid.getPhoneNumberHome(0).getTelephoneNumber().setValue(getTagValue("TELEPHONE_DAY", element));
                }
            }

            // Convert the HL7 message to a pipe-delimited string
            PipeParser parser = new PipeParser();
            String encodedMessage = parser.encode(adt);
            System.out.println("HL7 Message: \n" + encodedMessage);

        } catch (HL7Exception | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private static boolean validateXMLSchema(InputStream xsdFile, InputStream xmlFile) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdFile));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            return true; // Validation successful
        } catch (Exception e) {
            System.out.println("XML Validation Error: " + e.getMessage());
            return false; // Validation failed
        }
    }
}
