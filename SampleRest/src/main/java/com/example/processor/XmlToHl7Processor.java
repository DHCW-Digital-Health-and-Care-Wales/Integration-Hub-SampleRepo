package com.example.processor;

import ca.uhn.hl7v2.model.v23.message.ADT_A01;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.PID;
import ca.uhn.hl7v2.parser.PipeParser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class XmlToHl7Processor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(XmlToHl7Processor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        String xml = exchange.getIn().getBody(String.class);

        // Extract values from XML (Use an XML parser in production)
        String patientId = xml.split("<id>")[1].split("</id>")[0];
        String patientName = xml.split("<name>")[1].split("</name>")[0];
        String dob = xml.split("<dob>")[1].split("</dob>")[0];
        LocalDate date = LocalDate.parse(dob, DateTimeFormatter.ISO_DATE);
        String HlDob = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Create HL7 ADT_A01 message
        ADT_A01 adtMessage = new ADT_A01();
        adtMessage.initQuickstart("ADT", "A01", "P");

        // Populate MSH Segment
        MSH mshSegment = adtMessage.getMSH();
        mshSegment.getSendingApplication().getNamespaceID().setValue("HOSPITAL");
        mshSegment.getSendingFacility().getNamespaceID().setValue("SYSTEM");

        // Populate PID Segment
        PID pidSegment = adtMessage.getPID();
//        pidSegment.getPatientID().getID().setValue(patientId);
        pidSegment.getPatientName(0).getFamilyName().setValue(patientName);
        pidSegment.getDateOfBirth().getTimeOfAnEvent().setValue(HlDob);
        pidSegment.getSetIDPatientID().setValue(patientId);


        // Convert HL7 object to Pipe Delimited String
        PipeParser pipeParser = new PipeParser();
        String hl7Message = pipeParser.encode(adtMessage);

        // Set the HL7 message in response
        exchange.getIn().setBody(hl7Message);
    }
}
