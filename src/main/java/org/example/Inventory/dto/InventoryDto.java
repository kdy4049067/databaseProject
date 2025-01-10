package org.example.Inventory.dto;

import org.example.Book.domain.Book;
import org.example.WareHouse.domain.WareHouse;

import java.util.List;

public record InventoryDto(int number, String code) {
}
