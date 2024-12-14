package org.example.Customer.service;

import jakarta.transaction.Transactional;
import org.example.Author.domain.Author;
import org.example.Author.repository.AuthorRepository;
import org.example.Award.domain.Award;
import org.example.Award.repository.AwardRepository;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
import org.example.Customer.domain.Customer;
import org.example.Customer.dto.CustomerDto;
import org.example.Customer.repository.CustomerRepository;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.PhoneCustomer.repository.PhoneCustomerRepository;
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


    public CustomerService(PhoneCustomerRepository phoneCustomerRepository, CustomerRepository customerRepository, AuthorRepository authorRepository, AwardRepository awardRepository, BookRepository bookRepository){
        this.phoneCustomerRepository = phoneCustomerRepository;
        this.customerRepository = customerRepository;
        this.authorRepository = authorRepository;
        this.awardRepository = awardRepository;
        this.bookRepository = bookRepository;
    }

    public PhoneCustomer makePhoneCustomer(String phone){
        return phoneCustomerRepository.findPhoneCustomerByPhone(phone);
    }

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
        return customerRepository.findCustomerByEmail(email);
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

    public List<Book> findBooksByAuthorName(String authorName) {
        List<Author> authors = authorRepository.findAuthorsByName(authorName);

        List<Book> books = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .collect(Collectors.toList());

        return books;
    }

    public List<Book> findBooksByAwardName(String awardName) {
        List<Award> awards = awardRepository.findAwardsByName(awardName);
        List<Book> books = new ArrayList<>();
        for(Award award : awards){
            String bookIsbn = award.getBook().getIsbn();
            Book book = bookRepository.findBookByIsbn(bookIsbn);

            if(book != null)
                books.add(book);
        }
        return books;
    }

    public List<Book> findBooksByTitle(String title){
        List<Book> books = bookRepository.findBooksByTitle(title);

        return books;
    }

    public boolean login(String email, String phone){
        Customer customer = customerRepository.findCustomerByEmail(email);
        if(customer == null) return false;
        if(!customer.getPhone().equals(phone)){
            return false;
        }
        return true;
    }

}

