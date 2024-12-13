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

    public WareHouse makeWareHouse(String code){
        return wareHouseRepository.findWareHouseByCode(code);
    }

    public InventoryDto insertInventory(Inventory inventory) {
        Inventory newInventory = inventoryRepository.save(inventory);
        return newInventory.toInventoryDto();
    }

    public List<InventoryDto> findAllInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
                .map(Inventory::toInventoryDto)
                .collect(Collectors.toList());
    }

    public Inventory findInventoryByBook(String bookIsbn) {
        return inventoryRepository.findInventoryByBook(bookIsbn);
    }

    @Transactional
    public InventoryDto updateInventory(Inventory inventory, InventoryDto inventoryDto) {
        Book book = makeBook(inventoryDto.bookIsbn());
        WareHouse wareHouse = makeWareHouse(inventoryDto.code());

        inventory.setNumber(inventoryDto.number());
        inventory.setBook(book);
        inventory.setWarehouse(wareHouse);

        return inventory.toInventoryDto();
    }

    public boolean isInventoryExist(Book book) {
        return inventoryRepository.existsInventoryByBook(book);
    }

    public void deleteInventory(String bookIsbn) {
        inventoryRepository.deleteInventoryByBook(bookIsbn);
    }
}

