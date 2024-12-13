package org.example.Inventory.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Book.domain.Book;
import org.example.Inventory.domain.Inventory;
import org.example.Inventory.dto.InventoryDto;
import org.example.Inventory.service.InventoryService;
import org.example.WareHouse.domain.WareHouse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventoryInsert")
    public String showInventoryInsertForm() {
        return "/inventory/inventoryInsert";
    }

    @PostMapping("/inventory/insert")
    public String manageInventory(@ModelAttribute InventoryDto inventoryDto) {
        int number = inventoryDto.number();
        String bookIsbn = inventoryDto.bookIsbn();
        String warehouseName = inventoryDto.code();

        Book book = inventoryService.makeBook(bookIsbn);
        WareHouse warehouse = inventoryService.makeWareHouse(warehouseName);

        Inventory inventory = new Inventory(number, book, warehouse);
        inventoryService.insertInventory(inventory);

        return "redirect:/inventory";
    }

    @GetMapping("/inventory")
    public String selectAllInventory(Model model) {
        List<InventoryDto> inventoryDtos = inventoryService.findAllInventory();
        model.addAttribute("inventory", inventoryDtos);

        return "inventory/inventorySelect";
    }

    @GetMapping("/inventory/update/{bookIsbn}")
    public String findUpdateInventory(Model model, @PathVariable(name = "bookIsbn") String bookIsbn) {
        Inventory inventory = inventoryService.findInventoryByBook(bookIsbn);
        InventoryDto findInventoryDto = inventory.toInventoryDto();
        model.addAttribute("inventoryDto", findInventoryDto);

        return "inventory/inventoryUpdate";
    }

    @PostMapping("/inventory/update/{bookIsbn}")
    public String updateInventory(@ModelAttribute InventoryDto inventoryDto, @PathVariable(name = "bookIsbn") String bookIsbn) {
        Inventory inventory = inventoryService.findInventoryByBook(bookIsbn);
        InventoryDto updatedInventoryDto = inventoryService.updateInventory(inventory, inventoryDto);
        if (updatedInventoryDto == null) {
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/inventory";
    }

    @PostMapping("/inventory/delete/{bookIsbn}")
    public String deleteInventory(@PathVariable(name = "bookIsbn") String bookIsbn) {
        inventoryService.deleteInventory(bookIsbn);

        return "redirect:/inventory";
    }
}
