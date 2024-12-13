package org.example.Inventory.repository;

import org.example.Book.domain.Book;
import org.example.Inventory.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    public Inventory findInventoryByBook(String bookIsbn);
    public boolean existsInventoryByBook(Book book);
    public void deleteInventoryByBook(String bookIsbn);
}
