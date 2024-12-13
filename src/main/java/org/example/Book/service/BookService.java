package org.example.Book.service;

import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Book.repository.BookRepository;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public BookDto insertBook(Book book){
        Book newBook = bookRepository.save(book);
        return newBook.toBookDto();
    }

}
