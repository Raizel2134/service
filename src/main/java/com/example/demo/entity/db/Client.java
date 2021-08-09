package com.example.demo.entity.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "guid")
    private int guid;
    @Column(name = "name")
    private String name;
    @Column(name = "date_birth")
    private String date_birth;
    @Column(name = "account_uid")
    private Long account_uid;
    @Column(name = "is_deleted")
    private boolean deleted;

    public Client(int guid, String name, String date_birth) {
        this.guid = guid;
        this.name = name;
        this.date_birth = date_birth;
    }
}
