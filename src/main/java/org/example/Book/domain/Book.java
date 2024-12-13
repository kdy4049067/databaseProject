package org.example.Book.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.Book.dto.BookDto;
import org.example.User.dto.UserDto;

@Entity(name = "Book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Book {

    @Id
    private String year;
    @Column
    private String title;
    @Column
    private int price;
    @Column
    private String category;
    @Column
    private String isbn;

    public BookDto toBookDto(){
        return new BookDto(
                this.year,
                this.title,
                this.price,
                this.category,
                this.isbn);
    }

}
