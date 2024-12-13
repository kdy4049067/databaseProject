package org.example.Author.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Award.Award;
import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Url.Url;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    private String name;  // 기본키

    @OneToOne
    @JoinColumn(name = "url")  // `urls` 테이블의 `url` 컬럼 참조
    private Url url;

    @OneToMany(mappedBy = "author")
    private List<Award> awards = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "author_has_book", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "author_name"), // Author의 외래 키
            inverseJoinColumns = @JoinColumn(name = "book_isbn") // Book의 외래 키
    )
    private List<Book> books;

    public Author(String name, Url url) {
        this.name = name;
        this.url = url;
    }
}

