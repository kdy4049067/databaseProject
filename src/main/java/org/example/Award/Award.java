package org.example.Award;

import jakarta.persistence.*;
import org.example.Author.domain.Author;
import org.example.Book.domain.Book;

import java.util.List;

@Entity
public class Award {

    @Id
    private String name;  // 기본키

    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_name")  // `author` 테이블의 `name` 참조
    private Author author;

    @OneToMany(mappedBy = "award", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    // 기본 생성자, getter, setter
}
