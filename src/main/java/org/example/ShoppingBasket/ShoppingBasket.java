package org.example.ShoppingBasket;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.Book.domain.Book;
import org.example.Contains.domain.Contains;
import org.example.Customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingBasket {

    @Id
    private String basketId;

    private LocalDateTime orderDate;

    @OneToOne
    @JoinColumn(name = "contains_id")
    private Contains contains;

    @OneToOne
    @JoinColumn(name="customer_email")
    private Customer customer;

}

