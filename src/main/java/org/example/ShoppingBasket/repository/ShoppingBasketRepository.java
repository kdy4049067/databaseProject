package org.example.ShoppingBasket.repository;

import org.example.ShoppingBasket.ShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, String> {

    public ShoppingBasket findShoppingBasketByBasketId(String basketId);
    public ShoppingBasket findShoppingBasketByCustomer(String email);
}
