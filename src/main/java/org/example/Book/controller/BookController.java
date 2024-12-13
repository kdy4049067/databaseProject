package org.example.Book.controller;

import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/book/insert")
    public String manageBook(@ModelAttribute BookDto bookDto){
        String isbn=  bookDto.isbn();
        String year = bookDto.year();
        int price = bookDto.price();
        String category = bookDto.category();
        String title = bookDto.title();

        Book book = new Book(year, title, price, category, isbn);
        bookService.insertBook(book);

        return "/bookSelect";
    }

    @GetMapping("/book")
    public String selectAllBook(Model model){
        List<BookDto> bookDtos = bookService.findAllBook();
        model.addAttribute("book", bookDtos);

        return "/bookSelect";
    }

}
