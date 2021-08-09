package com.example.demo.entity.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    private Long id;
    private int guid;
    private int account_uid;
}
