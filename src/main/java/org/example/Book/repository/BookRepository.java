package org.example.Book.repository;

import org.example.Book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

    public boolean existsByIsbn(String isbn);
    public Book findBookByIsbn(String isbn);

}
