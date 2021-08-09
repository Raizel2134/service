package com.example.demo.entity.xml;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "clients")
@XmlRootElement(name = "clients")
@ToString
public class ClientsXML {
    @XmlElement(name = "client")
    private List<ClientXML> clients;

    public List<ClientXML> getClients() {
        return clients;
    }

    public void printClients(){
        for (ClientXML client: clients) {
            System.out.println("Имя: " + client.name + "\nДата рождения: " + client.date_birth + '\n');
        }
    }
}
