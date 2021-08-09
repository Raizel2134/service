package com.example.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Client {
    private Long id;
    private int guid;
    private String name;
    private String date_birth;
    private Long account_uid;
    private boolean deleted;
}
