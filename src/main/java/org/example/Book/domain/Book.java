package org.example.Book.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Author.domain.Author;
import org.example.Award.domain.Award;
import org.example.Book.dto.BookDto;
import org.example.Contains.domain.Contains;
import org.example.Inventory.domain.Inventory;
import org.example.Reservation.Reservation;
import org.example.ShoppingBasket.ShoppingBasket;

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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Award> awards;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Contains contains;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Inventory inventory;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_has_reservation",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    private List<Reservation> reservations; // Reservation과 N대 M 관계

}
