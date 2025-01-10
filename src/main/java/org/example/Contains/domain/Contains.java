package org.example.Contains.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Book.domain.Book;
import org.example.Contains.dto.ContainsDto;
import org.example.ShoppingBasket.ShoppingBasket;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contains {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int number;

    @OneToMany(mappedBy = "contains", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    @OneToOne(mappedBy = "contains", cascade = CascadeType.ALL)
    private ShoppingBasket shoppingBasket;

    public Contains(int number) {
        this.number = number;
    }

    public ContainsDto toContainsDto() {
        return new ContainsDto(
                this.id,
                this.number);
    }

    public void addBook(Book book){
        this.books.add(book);
    }

}
