package org.example.WareHouse;

import jakarta.persistence.*;
import org.example.Book.domain.Book;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WareHouse {

    @Id
    private String code;  // 기본키

    private String address;
    private String phone;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

}
