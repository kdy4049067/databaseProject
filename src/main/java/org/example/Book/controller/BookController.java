package org.example.Book.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/bookInsert")
    public String showBookInsertForm() {
        // bookInsert.html 페이지로 이동
        return "/book/bookInsert";
    }

    @PostMapping("/book/insert")
    public String manageBook(@ModelAttribute BookDto bookDto){
        String isbn=  bookDto.isbn();
        String year = bookDto.year();
        int price = bookDto.price();
        String category = bookDto.category();
        String title = bookDto.title();

        Book book = new Book(isbn, title, price, category, year);
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
    public String findUpdateBook(Model model, @PathVariable(name = "isbn") String isbn){
        Book book = bookService.findBookByIsbn(isbn);
        BookDto findBookDto = book.toBookDto();
        model.addAttribute("bookDto", findBookDto);

        return "book/bookUpdate";
    }

    @PostMapping("/book/update/{isbn}")
    public String updateBook(@ModelAttribute BookDto bookDto, @PathVariable(name = "isbn") String isbn){
        Book book = bookService.findBookByIsbn(isbn);
        BookDto updatedBookDto = bookService.updateBook(book, bookDto);
        if(updatedBookDto == null){
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/book";
    }

    @PostMapping("/book/delete/{isbn}")
    public String deleteBook(@PathVariable(name = "isbn") String isbn){
        bookService.deleteBook(isbn);

        return "redirect:/book";
    }

}
