package org.example.Award.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Author.domain.Author;
import org.example.Award.dto.AwardDto;
import org.example.Book.domain.Book;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Award {

    @Id
    private String name;
    @Column
    private String year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_name")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_isbn")
    private Book book;

    public Award(String name, String year, Author author, Book book) {
        this.name = name;
        this.year = year;
        this.author = author;
        this.book = book;
    }

    public AwardDto toAwardDto(){
        return new AwardDto(
                this.name,
                this.year,
                this.author.getName(),
                this.book.getIsbn());
    }

}
