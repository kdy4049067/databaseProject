package org.example.Contains.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Book.domain.Book;
import org.example.Contains.dto.ContainsDto;
import org.example.ShoppingBasket.ShoppingBasket;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contains {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int number;  // 책의 수량

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn", unique = true)
    private Book book;  // 책 엔티티

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_basket_id")
    private ShoppingBasket shoppingBasket;

    public Contains(int number, Book book, ShoppingBasket shoppingBasket) {
        this.number = number;
        this.book = book;
        this.shoppingBasket = shoppingBasket;
    }

    public ContainsDto toContainsDto() {
        return new ContainsDto(
                this.number,
                this.book.getIsbn(),
                this.shoppingBasket.getBasketId());
    }
}
