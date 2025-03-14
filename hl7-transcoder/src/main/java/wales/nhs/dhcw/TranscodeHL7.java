package wales.nhs.dhcw;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class TranscodeHL7 {
    public static void main(String[] args) throws HL7Exception, XMLStreamException, IOException {
        File input = getInputFile(args);

        var context = new DefaultHapiContext();
        var pipeParser = context.getPipeParser();
        var xmlParser = context.getXMLParser();
        var result = "";
        String sourceMessage = readTextFile(input);

        if (isXml(input)) {
            Message msg = xmlParser.parse(sourceMessage);
            result = pipeParser.encode(msg);
        } else {
            result = xmlParser.encode(pipeParser.parse(sourceMessage));
        }

        PrintWriter output = new PrintWriter(getOutputFile(args));
        output.print(result);
        output.close();
    }

    private static String readTextFile(File input) throws IOException {
        return Files.readString(input.toPath(), StandardCharsets.UTF_8);
    }


    private static boolean isXml(File input) {
        return input.getPath().endsWith(".xml");
    }

    private static File getInputFile(String[] args) {
        if (null == args || args.length < 1) {
            throw new RuntimeException("File name not given");
        }
        var file = new File(args[0]);
        if (!file.isFile()) {
            throw new RuntimeException("File not found: " + args[1]);
        }
        return file;
    }

    private static File getOutputFile(String[] args) {
        if (null == args || args.length < 2) {
            throw new RuntimeException("File name not given");
        }
        var file = new File(args[1]);
        if (file.isFile()) {
            throw new RuntimeException("File already exists: " + args[1]);
        }
        return file;
    }
}