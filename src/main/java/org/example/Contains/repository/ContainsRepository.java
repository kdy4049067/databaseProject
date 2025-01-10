package org.example.Contains.repository;

import org.example.Book.domain.Book;
import org.example.Contains.domain.Contains;
import org.example.ShoppingBasket.ShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainsRepository extends JpaRepository<Contains, Long> {

    public void deleteContainsByShoppingBasket(ShoppingBasket shoppingBasket);
    public Contains findContainsByShoppingBasket(ShoppingBasket shoppingBasket);

}
