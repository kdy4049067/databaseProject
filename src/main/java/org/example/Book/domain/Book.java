package org.example.Book.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Author.domain.Author;
import org.example.Award.Award;
import org.example.Book.dto.BookDto;
import org.example.Reservation.Reservation;
import org.example.ShoppingBasket.ShoppingBasket;
import org.example.User.dto.UserDto;
import org.example.WareHouse.WareHouse;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    private String isbn;
    @Column
    private String title;
    @Column
    private int price;
    @Column
    private String category;
    @Column
    private String year;

    public Book(String isbn, String title, int price, String category, String year) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.category = category;
        this.year = year;
    }

    public BookDto toBookDto(){
        return new BookDto(
                this.isbn,
                this.title,
                this.price,
                this.category,
                this.isbn);
    }

    @ManyToOne
    @JoinColumn(name = "award_name")
    private Award award; // Award와 1대 N 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id") // ShoppingBasket의 basketId를 외래 키로 설정
    private ShoppingBasket shoppingBasket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_code") // Warehouse의 code를 외래 키로 설정
    private WareHouse warehouse;

    @ManyToMany(mappedBy = "books") // `mappedBy`를 사용하여 관계의 주체를 `Author`로 지정
    private List<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_has_reservation",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    private List<Reservation> reservations; // Reservation과 N대 M 관계

}
