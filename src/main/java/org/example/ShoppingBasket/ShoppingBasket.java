package org.example.ShoppingBasket;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.example.Book.domain.Book;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingBasket {

    @Id
    private String basketId;  // 기본키

    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "shoppingBasket")
    private List<Book> books = new ArrayList<>();

    // 기본 생성자, getter, setter
}

