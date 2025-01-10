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

    public ShoppingBasket makeShoppingBasket(String shoppingBasketId) {
        return shoppingBasketRepository.findShoppingBasketByBasketId(shoppingBasketId);
    }

    @Transactional
    public Contains insertContains(Contains contains) {
        return containsRepository.save(contains);
    }

    public List<ContainsDto> findAllContains() {
        List<Contains> containsList = containsRepository.findAll();
        return containsList.stream()
                .map(Contains::toContainsDto)
                .collect(Collectors.toList());
    }

    public Contains findContainsByShoppingBasket(ShoppingBasket shoppingBasket) {
        return containsRepository.findContainsByShoppingBasket(shoppingBasket);
    }

    @Transactional
    public ContainsDto updateContains(Contains contains, ContainsDto containsDto) {
        contains.setNumber(containsDto.number());

        return contains.toContainsDto();
    }

    @Transactional
    public void deleteContains(String shoppingBasketId) {
        ShoppingBasket shoppingBasket = shoppingBasketRepository.findShoppingBasketByBasketId(shoppingBasketId);
        containsRepository.deleteContainsByShoppingBasket(shoppingBasket);
    }

}
