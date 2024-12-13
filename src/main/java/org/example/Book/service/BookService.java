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

    public Book findBookByIsbn(String isbn){
        return bookRepository.findBookByIsbn(isbn);
    }

    public BookDto updateBook(Book book, BookDto bookDto){
        boolean check = isIsbnExist(bookDto.isbn());
        if(check){
            throw new IllegalArgumentException("이미 존재하는 isbn입니다.");
        }

        book.setYear(bookDto.year());
        book.setIsbn(bookDto.isbn());
        book.setTitle(bookDto.title());
        book.setPrice(bookDto.price());
        book.setCategory(bookDto.category());

        return book.toBookDto();
    }

    public boolean isIsbnExist(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }


}
