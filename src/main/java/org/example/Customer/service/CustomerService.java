package org.example.Customer.service;

import jakarta.transaction.Transactional;
import org.example.Author.domain.Author;
import org.example.Author.repository.AuthorRepository;
import org.example.Award.domain.Award;
import org.example.Award.repository.AwardRepository;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
import org.example.Contains.domain.Contains;
import org.example.Contains.repository.ContainsRepository;
import org.example.Customer.domain.Customer;
import org.example.Customer.dto.CustomerDto;
import org.example.Customer.repository.CustomerRepository;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.PhoneCustomer.repository.PhoneCustomerRepository;
import org.example.Reservation.domain.Reservation;
import org.example.Reservation.repository.ReservationRepository;
import org.example.ShoppingBasket.ShoppingBasket;
import org.example.ShoppingBasket.repository.ShoppingBasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final PhoneCustomerRepository phoneCustomerRepository;
    private final CustomerRepository customerRepository;
    private final AuthorRepository authorRepository;
    private final AwardRepository awardRepository;
    private final BookRepository bookRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;
    private final ReservationRepository reservationRepository;
    private final ContainsRepository containsRepository;

    public CustomerService(PhoneCustomerRepository phoneCustomerRepository, CustomerRepository customerRepository, AuthorRepository authorRepository, AwardRepository awardRepository, BookRepository bookRepository, ShoppingBasketRepository shoppingBasketRepository, ReservationRepository repository, ReservationRepository reservationRepository, ContainsRepository containsRepository){
        this.phoneCustomerRepository = phoneCustomerRepository;
        this.customerRepository = customerRepository;
        this.authorRepository = authorRepository;
        this.awardRepository = awardRepository;
        this.bookRepository = bookRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.reservationRepository = reservationRepository;
        this.containsRepository = containsRepository;
    }

    public PhoneCustomer makePhoneCustomer(String phone){
        return phoneCustomerRepository.findPhoneCustomerByPhone(phone);
    }

    @Transactional
    public CustomerDto insertCustomer(Customer customer){
        PhoneCustomer phoneCustomer = phoneCustomerRepository.findPhoneCustomerByPhone(customer.getPhone());
        customer.setPhoneCustomer(phoneCustomer);
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer.toCustomerDto();
    }

    public List<CustomerDto> findAllCustomer(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(Customer::toCustomerDto)
                .collect(Collectors.toList());
    }

    public Customer findCustomerByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public CustomerDto updateCustomer(Customer customer, CustomerDto customerDto){
        if (!customer.getEmail().equals(customerDto.email()) && isEmailExist(customerDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 email입니다.");
        }

        PhoneCustomer phoneCustomer = phoneCustomerRepository.findPhoneCustomerByPhone(customerDto.phone());
        customer.setEmail(customerDto.email());
        customer.setPhone(customerDto.phone());
        customer.setPhoneCustomer(phoneCustomer);

        return customer.toCustomerDto();
    }

    public boolean isEmailExist(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Transactional
    public void deleteCustomer(String email) {
        customerRepository.deleteCustomerByEmail(email);
    }

    public List<Book> findBookByAuthorName(String authorName) {
        Author author = authorRepository.findAuthorByName(authorName);

        List<Book> books = author.getBooks();

        return books;
    }

    public int totalBookByAuthorName(String authorName){
        Author author = authorRepository.findAuthorByName(authorName);
        int num = 0;
        List<Book> books = author.getBooks();
        for(Book book : books){
            num += book.getInventory().getNumber();
        }
        return num;
    }

    public Book findBookByAwardName(String awardName) {
        Award award = awardRepository.findAwardByName(awardName);
        String bookIsbn = award.getBook().getIsbn();

        return bookRepository.findBookByIsbn(bookIsbn);
    }

    public int totalBookByAwardName(String awardName){
        Award award = awardRepository.findAwardByName(awardName);
        int num = award.getBook().getInventory().getNumber();
        return num;
    }

    public Book findBookByTitle(String title){
        return bookRepository.findBookByTitle(title);
    }

    public int totalBookByTitle(String title){
        Book book = bookRepository.findBookByTitle(title);
        int num = book.getInventory().getNumber();
        return num;
    }

    public boolean login(String email, String phone){
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) return false;
        if(!customer.getPhone().equals(phone)){
            return false;
        }
        return true;
    }

    public ShoppingBasket findMyShoppingBasket(String email){
        Customer customer = customerRepository.findByEmail(email);
        return customer.getShoppingBasket();
    }

    public Reservation findMyReservation(String email){
        Customer customer = customerRepository.findByEmail(email);
        return reservationRepository.findReservationByCustomer(customer);
    }

}

