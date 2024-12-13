package org.example.Book.service;

import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Book.repository.BookRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public BookDto insertBook(Book book){
        Book newBook = bookRepository.save(book);
        return newBook.toBookDto();
    }

    public List<BookDto> findAllBook(){
        List<Book> book = bookRepository.findAll();
        return book.stream()
                .map(Book::toBookDto)
                .collect(Collectors.toList());
    }

}
