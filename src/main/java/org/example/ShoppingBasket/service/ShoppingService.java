package org.example.ShoppingBasket.service;

import jakarta.transaction.Transactional;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
import org.example.Contains.domain.Contains;
import org.example.Contains.repository.ContainsRepository;
import org.example.Customer.domain.Customer;
import org.example.Customer.repository.CustomerRepository;
import org.example.ShoppingBasket.ShoppingBasket;
import org.example.ShoppingBasket.repository.ShoppingBasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShoppingBasketRepository shoppingBasketRepository;

    @Autowired
    private ContainsRepository containsRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public String purchaseBooks(String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail);

        ShoppingBasket shoppingBasket = shoppingBasketRepository.findShoppingBasketByCustomer(customer);

        Contains contains = shoppingBasket.getContains();

            for (Book book : contains.getBooks()) {
                if (contains.getNumber() <= 0) {
                    return "Insufficient stock for book: " + book.getTitle();
                }
                contains.setNumber(contains.getNumber() - 1);
                containsRepository.save(contains);
            }


        shoppingBasket.setOrderDate(LocalDateTime.now());
        shoppingBasketRepository.save(shoppingBasket);

        return "Purchase successful!";
    }
}
