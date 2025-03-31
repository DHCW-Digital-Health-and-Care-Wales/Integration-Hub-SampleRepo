package wales.nhs.dhcw.inthub.sample.sbcon;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import wales.nhs.dhcw.inthub.sample.sbcon.lib.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Sender {
    public static void main(String[] args) throws Exception {
        var config = Config.readConfig();

        // Create a ServiceBusClientBuilder
        ServiceBusClientBuilder builder = new ServiceBusClientBuilder()
            .connectionString(config.connectionString());

        // Sending a message
        ServiceBusSenderClient senderClient = builder
            .sender()
            .queueName(config.ingressQueueName())
            .buildClient();

        String validXML = """
            <?xml version="1.0" encoding="UTF-16"?>
            <!--SCOPEDATA-->
            <MAINDATA xmlns="http://PAS_Demographics">\s
                <TRANSACTION>
                    <TRANSACTION_ID>28037909</TRANSACTION_ID>
                    <MSG_ID>MPI</MSG_ID>
                    <UNIT_NUMBER>B6158954</UNIT_NUMBER>
                    <NHS_NUMBER>""</NHS_NUMBER>
                    <SURNAME>BIDEN</SURNAME>
                    <FORENAME>JOE</FORENAME>
                    <TITLE>Mr.</TITLE>
                    <BIRTHDATE>1968-06-12</BIRTHDATE>
                    <DEATHDATE></DEATHDATE>
                    <SEX>M</SEX>
                    <ADDRESS_1>Betsi Cadwaladr University Health Board</ADDRESS_1>
                    <ADDRESS_2>Ysbyty Gwynedd</ADDRESS_2>
                    <ADDRESS_3>Penrhosgarnedd</ADDRESS_3>
                    <ADDRESS_4>Bangor,Gwynedd</ADDRESS_4>
                    <ADDRESS_5>""</ADDRESS_5>
                    <POSTCODE>LL57 2PW</POSTCODE>
                    <TELEPHONE_DAY>""</TELEPHONE_DAY>
                    <TELEPHONE_NIGHT>""</TELEPHONE_NIGHT>
                    <OVERSEAS_STATUS>8</OVERSEAS_STATUS>
                    <MARITAL_STATUS>2</MARITAL_STATUS>
                    <DISABLED_STATUS>9</DISABLED_STATUS>
                    <ETHNIC_ORIGIN>ZZ</ETHNIC_ORIGIN>
                    <RELIGION_STATUS>A</RELIGION_STATUS>
                    <REGISTERED_GP>G9538649</REGISTERED_GP>
                    <GP_PRACTICE>W94609</GP_PRACTICE>
                    <NHS_CERTIFICATION>""</NHS_CERTIFICATION>
                    <GP_SURNAME>BRITTO</GP_SURNAME>
                    <GP_INITS>M</GP_INITS>
                    <GP_ADDR1>CORWEN HOUSE</GP_ADDR1>
                    <GP_ADDR2>MARKET PLACE</GP_ADDR2>
                    <GP_ADDR3>PENYGROES</GP_ADDR3>
                    <GP_ADDR4>GWYNEDD</GP_ADDR4>
                    <GP_POSTCODE>LL54 6NN</GP_POSTCODE>
                    <GP_TELEPHONE>01286 880336</GP_TELEPHONE>
                    <GP_FAX>""</GP_FAX>
                    <MOBILE>""</MOBILE>
                    <EMAIL>""</EMAIL>
                    <DHA_CODE>7A1</DHA_CODE>
                    <NEXT_KIN>""</NEXT_KIN>
                    <NEXT_KIN_REL>""</NEXT_KIN_REL>
                    <NEXT_KIN_ADDR1>""</NEXT_KIN_ADDR1>
                    <NEXT_KIN_ADDR2>""</NEXT_KIN_ADDR2>
                    <NEXT_KIN_ADDR3>""</NEXT_KIN_ADDR3>
                    <NEXT_KIN_ADDR4>""</NEXT_KIN_ADDR4>
                    <NEXT_KIN_POSTCODE>""</NEXT_KIN_POSTCODE>
                    <OTHER_KIN>""</OTHER_KIN>
                    <OTHER_KIN_REL>""</OTHER_KIN_REL>
                    <OTHER_KIN_ADDR1>""</OTHER_KIN_ADDR1>
                    <OTHER_KIN_ADDR2>""</OTHER_KIN_ADDR2>
                    <OTHER_KIN_ADDR3>""</OTHER_KIN_ADDR3>
                    <OTHER_KIN_ADDR4>""</OTHER_KIN_ADDR4>
                    <OTHER_KIN_POSTCODE>""</OTHER_KIN_POSTCODE>
                    <NEXT_KIN_TEL_DAY>""</NEXT_KIN_TEL_DAY>
                    <NEXT_KIN_TEL_NIGHT>""</NEXT_KIN_TEL_NIGHT>
                    <OTHER_KIN_TEL_DAY>""</OTHER_KIN_TEL_DAY>
                    <OTHER_KIN_TEL_NIGHT>""</OTHER_KIN_TEL_NIGHT>
                    <MAIDEN_NAME>""</MAIDEN_NAME>
                    <ALIAS_FORENAME>""</ALIAS_FORENAME>
                    <ALIAS_SURNAME>""</ALIAS_SURNAME>
                    <REQUESTED_NAME>""</REQUESTED_NAME>
                    <OCCUPATION>""</OCCUPATION>
                    <CONTACT_ADDRESS_1>Betsi Cadwaladr University Health Board</CONTACT_ADDRESS_1>
                    <CONTACT_ADDRESS_2>Ysbyty Gwynedd</CONTACT_ADDRESS_2>
                    <CONTACT_ADDRESS_3>Penrhosgarnedd</CONTACT_ADDRESS_3>
                    <CONTACT_ADDRESS_4>Bangor,Gwynedd</CONTACT_ADDRESS_4>
                    <CONTACT_ADDRESS_5>""</CONTACT_ADDRESS_5>
                    <CONTACT_ADDRESS_6>LL57 2PW</CONTACT_ADDRESS_6>
                    <EVENT_CODES>1400</EVENT_CODES>
                    <KEY_NOTE_TYPES>3</KEY_NOTE_TYPES>
                    <OTHER_ALIAS>""</OTHER_ALIAS>
                    <GENDER>""</GENDER>
                    <PREFERRED_LANGUAGE>2</PREFERRED_LANGUAGE>
                    <UPDATE_DATE>2025-02-06T08:36:12</UPDATE_DATE>
                    <USER_ID>BCLUAT448</USER_ID>
                    <SYSTEM_ID>102</SYSTEM_ID>
                    <EPISODE_NO>""</EPISODE_NO>
                    <SOURCE_ADMISSION>19 Usual place of residence</SOURCE_ADMISSION>
                    <SOURCE_ADMISSION_DESC>Usual place of residence</SOURCE_ADMISSION_DESC>
                    <CONS>CONSG</CONS>
                    <SPEC>110000</SPEC>
                    <WARD>WAU55</WARD>
                    <TRT_DATE>2025-02-07</TRT_DATE>
                    <TRT_TIME>0800</TRT_TIME>
                    <DIS_DATE>""</DIS_DATE>
                    <DIS_TIME>""</DIS_TIME>
                    <TRT_TYPE>AT</TRT_TYPE>
                    <LINK_ID>M11183192</LINK_ID>
                    <UNIQUE_ID>15592325</UNIQUE_ID>
                    <OUTCOME>""</OUTCOME>
                    <CATEGORY>10</CATEGORY>
                    <URGENCY>3</URGENCY>
                    <OPERATION_DATE>""</OPERATION_DATE>
                    <LIST_OUTCOME>43</LIST_OUTCOME>
                    <INT_MAN>1</INT_MAN>
                    <COMMENTS>*** Not Latest Admission *** to come back to SDEC tomorrow</COMMENTS>
                    <CLINICAL_CONDITION>""</CLINICAL_CONDITION>
                    <TCI_DATE>2025-02-07</TCI_DATE>
                    <CONS_NAME>Mixed Clinicians</CONS_NAME>
                    <SPEC_NAME>Orthopaedics</SPEC_NAME>
                    <CONS_GMC>N0000210</CONS_GMC>
                    <WARD_NAME>Same Day Emergency Care (SDEC) YG (West)</WARD_NAME>
                    <CURLOC_PROVIDER_CODE>7A1AU</CURLOC_PROVIDER_CODE>
                    <SPELL_NO>11 Elective - waiting list</SPELL_NO>
                    <TRANSFERS>
                    </TRANSFERS>
                    <ADM_CONS>CONSG</ADM_CONS>
                    <ADM_SPEC>110000</ADM_SPEC>
                    <ADM_CONS_NAME>Mixed Clinicians</ADM_CONS_NAME>
                    <ADM_SPEC_NAME>Orthopaedics</ADM_SPEC_NAME>
                    <ADM_CONS_GMC>N0000210</ADM_CONS_GMC>
                    <ADM_WARD>WAU55</ADM_WARD>
                    <ADM_WARD_NAME>Same Day Emergency Care (SDEC) YG (West)</ADM_WARD_NAME>
                    <DISDEST>""</DISDEST>
                    <DISDEST_DESC>""</DISDEST_DESC>
                    <DISLOCATION>""</DISLOCATION>
                    <DISLOCATION_DESC>""</DISLOCATION_DESC>
                    <OUTCOME_DESC>""</OUTCOME_DESC>
                    <UPI>7A110699992</UPI>
                    <UPI_EVENT>61</UPI_EVENT>
                    <UPI_SOURCE>MA</UPI_SOURCE>
                    <UPI_EVENT_DESC>Treatment commenced today</UPI_EVENT_DESC>
                    <UPI_SOURCE_DESC>Admission via Myrddin</UPI_SOURCE_DESC>
                    <UPI_EVENT_DATE>07/02/2025</UPI_EVENT_DATE>
                    <UPI_STOP_DATE>""</UPI_STOP_DATE>
                    <HOSPITAL>""</HOSPITAL>
                    <HOSPITAL_DESC>""</HOSPITAL_DESC>
                    <ACT_REQ_OPTIONS>
                    </ACT_REQ_OPTIONS>
                    <OP_DOCUMENT>""</OP_DOCUMENT>
                    <ELEC_REPRO_SET>""</ELEC_REPRO_SET>
                    <ELEC_REPRO_LEVEL>""</ELEC_REPRO_LEVEL>
                    <ELEC_REPRO_SET_BY>""</ELEC_REPRO_SET_BY>
                    <ELEC_REPRO_TARGET>""</ELEC_REPRO_TARGET>
                </TRANSACTION>
            </MAINDATA>
            """;
        System.out.println("sending");
        senderClient.sendMessage(new ServiceBusMessage(validXML.getBytes(StandardCharsets.UTF_16)));
        System.out.println("Sent : " + validXML);
        senderClient.close();

    }
}
