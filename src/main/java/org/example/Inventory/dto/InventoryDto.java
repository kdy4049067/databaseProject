package org.example.Inventory.dto;

import org.example.Book.domain.Book;
import org.example.WareHouse.domain.WareHouse;

public record InventoryDto(int number, String bookIsbn, String code) {
}
