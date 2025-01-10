package org.example.Book.service;

import jakarta.transaction.Transactional;
import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Transactional
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

    @Transactional
    public BookDto updateBook(Book book, BookDto bookDto){
        if (!book.getIsbn().equals(bookDto.isbn()) && isIsbnExist(bookDto.isbn())) {
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

    @Transactional
    public void deleteBook(String isbn) {
        bookRepository.deleteBookByIsbn(isbn);
    }

}
