package org.example.Contains.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Book.domain.Book;
import org.example.Contains.domain.Contains;
import org.example.Contains.dto.ContainsDto;
import org.example.Contains.service.ContainsService;
import org.example.ShoppingBasket.ShoppingBasket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class ContainsController {

    private final ContainsService containsService;

    public ContainsController(ContainsService containsService) {
        this.containsService = containsService;
    }

    @GetMapping("/containsInsert")
    public String showContainsInsertForm() {
        return "/contains/containsInsert";
    }

    @PostMapping("/contains/insert")
    public String manageContains(@ModelAttribute ContainsDto containsDto) {
        int number = containsDto.number();
        String bookIsbn = containsDto.bookIsbn();
        String shoppingBasketId = containsDto.shoppingBasketId();

        Book book = containsService.makeBook(bookIsbn);
        ShoppingBasket shoppingBasket = containsService.makeShoppingBasket(shoppingBasketId);

        Contains contains = new Contains(number, book, shoppingBasket);
        containsService.insertContains(contains);

        return "redirect:/contains";
    }

    @GetMapping("/contains")
    public String selectAllContains(Model model) {
        List<ContainsDto> containsDtos = containsService.findAllContains();
        model.addAttribute("contains", containsDtos);

        return "contains/containsSelect";
    }

    @GetMapping("/contains/update/{bookIsbn}")
    public String findUpdateContains(Model model, @PathVariable(name = "bookIsbn") String bookIsbn) {
        Contains contains = containsService.findContainsByBook(bookIsbn);
        ContainsDto findContainsDto = contains.toContainsDto();
        model.addAttribute("containsDto", findContainsDto);

        return "contains/containsUpdate";
    }

    @PostMapping("/contains/update/{bookIsbn}")
    public String updateContains(@ModelAttribute ContainsDto containsDto, @PathVariable(name = "bookIsbn") String bookIsbn) {
        Contains contains = containsService.findContainsByBook(bookIsbn);
        ContainsDto updatedContainsDto = containsService.updateContains(contains, containsDto);
        if (updatedContainsDto == null) {
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/contains";
    }

    @PostMapping("/contains/delete/{bookIsbn}")
    public String deleteContains(@PathVariable(name = "bookIsbn") String bookIsbn) {
        containsService.deleteContains(bookIsbn);

        return "redirect:/contains";
    }
}
