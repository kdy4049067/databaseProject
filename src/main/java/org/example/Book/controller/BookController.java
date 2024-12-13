package org.example.Book.controller;

import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

        return "redirect:/book";
    }

    @GetMapping("/book")
    public String selectAllBook(Model model){
        List<BookDto> bookDtos = bookService.findAllBook();
        model.addAttribute("book", bookDtos);

        return "book/bookSelect";
    }

    @GetMapping("/book/update/{isbn}")
    public String findUpdateBook(Model model, @PathVariable String isbn){
        Book book = bookService.findBookByIsbn(isbn);
        BookDto findBookDto = book.toBookDto();
        model.addAttribute("bookDto", findBookDto);

        return "book/bookUpdate";
    }

    @PostMapping("/book/update/{isbn}")
    public String updateBook(@ModelAttribute BookDto bookDto, @PathVariable String isbn){
        Book book = bookService.findBookByIsbn(isbn);
        bookService.updateBook(book, bookDto);

        return "redirect:/book";
    }

    @PostMapping("/book/delete/{isbn}")
    public String deleteBook(@PathVariable String isbn){
        bookService.deleteBook(isbn);

        return "redirect:/book";
    }


}
