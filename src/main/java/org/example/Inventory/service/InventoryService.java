package org.example.Inventory.service;

import jakarta.transaction.Transactional;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
import org.example.Inventory.domain.Inventory;
import org.example.Inventory.dto.InventoryDto;
import org.example.Inventory.repository.InventoryRepository;
import org.example.WareHouse.domain.WareHouse;
import org.example.WareHouse.repository.WareHouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BookRepository bookRepository;
    private final WareHouseRepository wareHouseRepository;

    public InventoryService(InventoryRepository inventoryRepository, BookRepository bookRepository, WareHouseRepository wareHouseRepository) {
        this.inventoryRepository = inventoryRepository;
        this.bookRepository = bookRepository;
        this.wareHouseRepository = wareHouseRepository;
    }

    public Book makeBook(String bookIsbn){
        return bookRepository.findBookByIsbn(bookIsbn);
    }

    @Transactional
    public WareHouse makeWareHouse(String code){
        WareHouse wareHouse = wareHouseRepository.findWareHouseByCode(code);
        if (wareHouse == null) {
            throw new IllegalArgumentException("Warehouse not found with code: " + code);
        }
        return wareHouse;
    }

    @Transactional
    public Inventory insertInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<InventoryDto> findAllInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
                .map(Inventory::toInventoryDto)
                .collect(Collectors.toList());
    }

    public Inventory findInventoryByWareHouse(WareHouse wareHouse) {
        return inventoryRepository.findInventoryByWarehouse(wareHouse);
    }

    @Transactional
    public InventoryDto updateInventory(Inventory inventory, InventoryDto inventoryDto) {
        WareHouse wareHouse = makeWareHouse(inventoryDto.code());

        inventory.setNumber(inventoryDto.number());
        inventory.setWarehouse(wareHouse);

        return inventory.toInventoryDto();
    }

    public boolean isInventoryExist(WareHouse wareHouse) {
        return inventoryRepository.existsInventoryByWarehouse(wareHouse);
    }

    @Transactional
    public void deleteInventory(String code) {
        WareHouse wareHouse = wareHouseRepository.findWareHouseByCode(code);
        inventoryRepository.deleteInventoryByWarehouse(wareHouse);
    }
}

