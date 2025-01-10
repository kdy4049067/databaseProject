package org.example.Book.repository;

import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Contains.domain.Contains;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    public boolean existsByIsbn(String isbn);
    public Book findBookByIsbn(String isbn);
    public void deleteBookByIsbn(String isbn);
    public Book findBookByTitle(String title);
    public List<Book> findAll();

}
