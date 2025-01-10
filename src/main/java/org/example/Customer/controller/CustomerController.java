package org.example.Customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Book.repository.BookRepository;
import org.example.Book.service.BookService;
import org.example.Contains.domain.Contains;
import org.example.Contains.repository.ContainsRepository;
import org.example.Customer.domain.Customer;
import org.example.Customer.dto.CustomerDto;
import org.example.Customer.repository.CustomerRepository;
import org.example.Customer.service.CustomerService;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.Reservation.domain.Reservation;
import org.example.ShoppingBasket.ShoppingBasket;
import org.example.ShoppingBasket.repository.ShoppingBasketRepository;
import org.example.User.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final ContainsRepository containsRepository;

    public CustomerController(CustomerService customerService, BookService bookService, BookRepository bookRepository, ContainsRepository containsRepository, ShoppingBasketRepository shoppingBasketRepository, CustomerRepository customerRepository){
        this.customerService = customerService;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.containsRepository = containsRepository;
    }

    @GetMapping("/customerInsert")
    public String showCustomerInsertForm() {
        return "/customer/customerInsert";
    }

    @PostMapping("/customer/insert")
    public String manageCustomer(@ModelAttribute CustomerDto customerDto){
        String email = customerDto.email();
        String phone = customerDto.phone();
        PhoneCustomer phoneCustomer = customerService.makePhoneCustomer(phone);

        Customer customer = new Customer(email, phone, phoneCustomer);
        customerService.insertCustomer(customer);

        return "redirect:/customer";
    }

    @GetMapping("/customer")
    public String selectAllCustomer(Model model){
        List<CustomerDto> customerDtos = customerService.findAllCustomer();
        model.addAttribute("customer", customerDtos);

        return "customer/customerSelect";
    }

    @GetMapping("/customer/update/{email}")
    public String findUpdateCustomer(Model model, @PathVariable(name = "email") String email){
        Customer customer = customerService.findCustomerByEmail(email);
        CustomerDto findCustomerDto = customer.toCustomerDto();
        model.addAttribute("customerDto", findCustomerDto);

        return "customer/customerUpdate";
    }

    @PostMapping("/customer/update/{email}")
    public String updateCustomer(@ModelAttribute CustomerDto customerDto, @PathVariable(name = "email") String email){
        Customer customer = customerService.findCustomerByEmail(email);
        CustomerDto updatedCustomerDto = customerService.updateCustomer(customer, customerDto);
        if(updatedCustomerDto == null){
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/customer";
    }

    @PostMapping("/customer/delete/{email}")
    public String deleteCustomer(@PathVariable(name = "email") String email){
        customerService.deleteCustomer(email);

        return "redirect:/customer";
    }

    @PostMapping("/customer/author/search")
    public String searchByAuthorName(@RequestParam(name="authorName") String authorName, Model model){
        try {
            int total = customerService.totalBookByAuthorName(authorName);
            model.addAttribute("total", total);
            List<Book> books = customerService.findBookByAuthorName(authorName);
            if (books.isEmpty()) {
                model.addAttribute("message", "No books found for the given author.");
            } else {
                model.addAttribute("books", books);
            }
            return "findBooksByAuthor"; // findBooksByAuthor.html로 리턴
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while searching for books.");
            return "findBooksByAuthor";
        }
    }

    @PostMapping("/customer/award/search")
    public String searchByAwardName(@RequestParam(name="awardName")String awardName, Model model){
        try {
            int total = customerService.totalBookByAwardName(awardName);
            model.addAttribute("total", total);
            Book books = customerService.findBookByAwardName(awardName);
            if (books == null) {
                model.addAttribute("message", "No books found for the given award.");
            } else {
                model.addAttribute("books", books);
            }
            return "findBooksByAward";
        }
        catch(Exception e){
            model.addAttribute("errorMessage", "error");
            return "findBooksByAward";
        }
    }

    @PostMapping("/customer/book/search")
    public String searchByBookTitle(@RequestParam(name="title") String title, Model model){
        try {
            int total = customerService.totalBookByTitle(title);
            model.addAttribute("total", total);
            Book book = customerService.findBookByTitle(title);
            if (book == null) {
                model.addAttribute("message", "No books found for the given award.");
            } else {
                model.addAttribute("books", book);
            }
            return "findBooksByTitle";
        }
        catch(Exception e) {
            model.addAttribute("errorMessage", "error");
            return "findBooksByTitle";
        }

    }

    @PostMapping("customer/login")
    public String login(@ModelAttribute CustomerDto customerDto, Model model) {
        boolean isAuthenticated = customerService.login(customerDto.email(), customerDto.phone());

        if(!isAuthenticated) {
            model.addAttribute("error","login failed");
            return "index";
        }
        model.addAttribute("email", customerDto.email());
        return "customer-home";
    }

    @GetMapping("/customer/{email}/shopping-basket")
    public String viewShoppingBasket(@PathVariable("email") String email, Model model){
        ShoppingBasket shoppingBasket = customerService.findMyShoppingBasket(email);
        Contains contains = shoppingBasket.getContains();
        List<Book> books = contains.getBooks();
        model.addAttribute("books", books);
        return "customer-shoppingBasket";
    }

    @GetMapping("/customer/{email}/reservation")
    public String viewReservation(@PathVariable("email") String email, Model model){
        Reservation reservation = customerService.findMyReservation(email);
        List<Book> books = reservation.getBooks();
        model.addAttribute("books", books);
        return "customer-reservation";
    }

    @GetMapping("/customer/{email}/book/showAllBooks")
    public String showAllBooks(@PathVariable("email") String email, Model model) {
        List<BookDto> books = bookService.findAllBook();
        model.addAttribute("books", books);
        model.addAttribute("email", email);
        return "book-list";
    }

    @PostMapping("/customer/{email}/shopping-basket/add")
    public String addToCart(@PathVariable("email") String email, @RequestParam(name="isbn") String isbn, @RequestParam(name="quantity") int quantity, Model model) {
        Customer customer = customerService.findCustomerByEmail(email);

        Book book = bookRepository.findById(isbn).orElseThrow(() -> new RuntimeException("Book not found"));

        ShoppingBasket shoppingBasket = customer.getShoppingBasket();

        Contains contains = shoppingBasket.getContains();
        log.info(String.valueOf(contains.getId()));
        boolean bookExists = false;
        for (Book b : contains.getBooks()) {
            if (b.getIsbn().equals(isbn)) {
                contains.setNumber(contains.getNumber() + quantity);
                bookExists = true;
                break;
            }
        }

        if (!bookExists) {
            contains.addBook(book);
            contains.setNumber(contains.getNumber() + quantity);
        }

        book.setContains(contains);
        containsRepository.save(contains);
        bookRepository.save(book);

        model.addAttribute("message", "Book added to shopping basket successfully!");
        return "redirect:/customer/" + email + "/shopping-basket";
    }

}
