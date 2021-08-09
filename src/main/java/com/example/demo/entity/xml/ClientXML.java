package com.example.demo.entity.xml;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;

@ToString
public class ClientXML {
    @XmlElement(name = "name")
    public String name;
    @XmlElement(name = "date_birth")
    public String date_birth;
}
