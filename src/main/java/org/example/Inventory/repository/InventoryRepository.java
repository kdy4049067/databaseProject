package org.example.Inventory.repository;

import org.example.Book.domain.Book;
import org.example.Inventory.domain.Inventory;
import org.example.WareHouse.domain.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    public Inventory findInventoryByWarehouse(WareHouse wareHouse);
    public boolean existsInventoryByWarehouse(WareHouse wareHouse);
    public void deleteInventoryByWarehouse(WareHouse wareHouse);
}
