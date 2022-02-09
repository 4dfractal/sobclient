package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class HeartbeatClient {
    private final Logger log = LogManager.getLogger(HeartbeatClient.class);

    public GenericMessage<byte[]> send() {
        log.info("Sending query");
        String message = getXmlRequest();
        return new GenericMessage<byte[]>(message.getBytes(StandardCharsets.UTF_8));
    }

    private String getXmlRequest() {
        AwsQuery query = new AwsQuery();
        query.setBid("BID");
        query.setOperator("operator name");
        query.setTimestamp(new Date());
        query.setWorkstation("workstation name");
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(AwsQuery.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(query, sw);
        } catch (JAXBException e) {
            log.error("Ошика");
        }
        return sw.toString();
    }

    public Object receive(byte[] payload, MessageHeaders messageHeaders) { // LATER: use transformer() to receive String here
        String messageStr = new String(payload);
        log.info("Responce: {}", messageStr);
        return null;
    }


    public GenericMessage<byte[]> send1() {
        log.info("Sending query 1");
        String message = getXmlRequest1();
        return new GenericMessage<byte[]>(message.getBytes(StandardCharsets.UTF_8));
    }

    private String getXmlRequest1() {
        AwsQuery query = new AwsQuery();
        query.setBid("BID 1");
        query.setOperator("operator name 1");
        query.setTimestamp(new Date());
        query.setWorkstation("workstation name 1");
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(AwsQuery.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(query, sw);
        } catch (JAXBException e) {
            log.error("Ошика");
        }
        return sw.toString();
    }

}
