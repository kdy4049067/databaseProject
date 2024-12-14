package org.example.Contains.service;

import jakarta.transaction.Transactional;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
import org.example.Contains.domain.Contains;
import org.example.Contains.dto.ContainsDto;
import org.example.Contains.repository.ContainsRepository;
import org.example.ShoppingBasket.ShoppingBasket;
import org.example.ShoppingBasket.repository.ShoppingBasketRepository;
import org.example.WareHouse.domain.WareHouse;
import org.example.WareHouse.repository.WareHouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContainsService {

    private final ContainsRepository containsRepository;
    private final BookRepository bookRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;

    public ContainsService(ContainsRepository containsRepository, BookRepository bookRepository, ShoppingBasketRepository shoppingBasketRepository) {
        this.containsRepository = containsRepository;
        this.bookRepository = bookRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
    }

    public Book makeBook(String bookIsbn) {
        return bookRepository.findBookByIsbn(bookIsbn);
    }

    public ShoppingBasket makeShoppingBasket(String basketId) {
        return shoppingBasketRepository.findShoppingBasketByBasketId(basketId);
    }

    public ContainsDto insertContains(Contains contains) {
        Contains newContains = containsRepository.save(contains);
        return newContains.toContainsDto();
    }

    public List<ContainsDto> findAllContains() {
        List<Contains> containsList = containsRepository.findAll();
        return containsList.stream()
                .map(Contains::toContainsDto)
                .collect(Collectors.toList());
    }

    public Contains findContainsByBook(String bookIsbn) {
        Book book = bookRepository.findBookByIsbn(bookIsbn);
        return containsRepository.findContainsByBook(book);
    }

    @Transactional
    public ContainsDto updateContains(Contains contains, ContainsDto containsDto) {
        Book book = makeBook(containsDto.bookIsbn());
        ShoppingBasket shoppingBasket = makeShoppingBasket(containsDto.shoppingBasketId());

        contains.setNumber(containsDto.number());
        contains.setBook(book);
        contains.setShoppingBasket(shoppingBasket);

        return contains.toContainsDto();
    }

    public boolean isContainsExist(Book book) {
        return containsRepository.existsContainsByBook(book);
    }

    public void deleteContains(String bookIsbn) {
        Book book = bookRepository.findBookByIsbn(bookIsbn);
        containsRepository.deleteContainsByBook(book);
    }
}
