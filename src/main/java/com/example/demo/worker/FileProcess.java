package com.example.demo.worker;

import com.example.demo.entity.db.Client;
import com.example.demo.entity.xml.ClientXML;
import com.example.demo.entity.xml.ClientsXML;
import com.example.demo.rabbit.Sender;
import com.example.demo.serialize.JAXBCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class FileProcess {
    private static final String HEADER_FILE_NAME = "file_name";
    private static final String MSG = "\n%s file received.\n";
    private static final String CLIENT_SCHEMA = "C:\\Users\\edyso\\Desktop\\test\\src\\main\\resources\\clients.xsd";
    private static final String DIRECTORY_OUTPUT = "C:\\Users\\edyso\\Desktop\\client\\output";
    private static final String DIRECTORY_ERRORS = "C:\\Users\\edyso\\Desktop\\client\\errors\\";

    private static final File fileClientSchema = new File(CLIENT_SCHEMA);
    private final Logger logger = Logger.getLogger(File.class.getName());

    private Random guidRandom = new Random();

    private final JAXBCustom jaxbContext;
    private final Sender sender;

    public void process(Message<String> msg) throws IOException {
        String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);

        logger.info(String.format(MSG, fileName));

        File fileInput = getFileInput(msg);
        File fileOutput = getFileOutput(fileName);

        try {
            checkValidate(fileInput);
            createOrUpdateFile(fileOutput, fileInput);
        } catch (Exception exception){
            createAndWriteExceptionInErrorFile(exception, fileName);
            logger.warning(exception.toString());
        }
    }

    private void checkValidate(File fileInput) throws SAXException, IOException {
        SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(new StreamSource(fileClientSchema))
                .newValidator()
                .validate(new StreamSource(fileInput));
    }

    private File getFileOutput(String fileName) {
        return new File(DIRECTORY_OUTPUT + "\\" + fileName);
    }

    private File getFileInput(Message<String> msg) {
        return new File(Objects.requireNonNull(msg.getHeaders().get("file_originalFile")).toString());
    }

    private void createAndWriteExceptionInErrorFile(Exception exception, String fileName) throws IOException {
        File fileError = new File(DIRECTORY_ERRORS + fileName.substring(0, fileName.indexOf(".")) + ".txt");
        writeExceptionInFile(exception, fileError);
    }

    private void writeExceptionInFile(Exception exception, File fileError) throws IOException {
        FileWriter writer = new FileWriter(fileError, false);
        writer.write(exception.toString());
        writer.flush();
        writer.close();
    }

    private void createOrUpdateFile(File fileOutput, File fileInput) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ClientsXML clients = (ClientsXML) unmarshaller.unmarshal(fileInput);

        clients.printClients();

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(clients, fileOutput);

        castAndSaveClients(clients);

        fileInput.delete();
    }

    private void castAndSaveClients(ClientsXML clients) {
        List<ClientXML> clientList = clients.getClients();
        List<Client> clientsListForDB = new ArrayList<>();
        for (ClientXML client: clientList) {
            clientsListForDB.add(new Client(guidRandom.nextInt(2000000) + 1, client.name, client.date_birth));
        }
        saveClients(clientsListForDB);
    }

    private void saveClients(List<Client> clientsListForDB) {
        for (Client client: clientsListForDB) {
            sender.sendMessage(client);
        }
    }
}
