package com.example.demo.serialize;

import com.example.demo.entity.xml.ClientsXML;
import org.springframework.stereotype.Component;

import javax.xml.bind.*;

@Component
public class JAXBCustom extends JAXBContext {
    private JAXBContext context;

    public JAXBCustom() throws JAXBException {
        this.context = JAXBContext.newInstance(ClientsXML.class);
    }

    @Override
    public Unmarshaller createUnmarshaller() throws JAXBException {
        return  context.createUnmarshaller();
    }

    @Override
    public Marshaller createMarshaller() throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return marshaller;
    }

    @Override
    public Validator createValidator() {
        return null;
    }
}
