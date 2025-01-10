package org.example.Contains.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Book.domain.Book;
import org.example.Contains.domain.Contains;
import org.example.Contains.dto.ContainsDto;
import org.example.Contains.service.ContainsService;
import org.example.ShoppingBasket.ShoppingBasket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String manageContains(@ModelAttribute ContainsDto containsDto){
        int number = containsDto.number();
        Contains contains = new Contains(number);
        containsService.insertContains(contains);

        return "redirect:/contains";
    }

    @GetMapping("/contains")
    public String selectAllContains(Model model) {
        List<ContainsDto> containsDtos = containsService.findAllContains();
        model.addAttribute("contains", containsDtos);

        return "contains/containsSelect";
    }

    @GetMapping("/contains/update/{shoppingBasketBasketId}")
    public String findUpdateContains(Model model, @PathVariable(name = "shoppingBasketBasketId") String shoppingBasketBasketId) {
        ShoppingBasket shoppingBasket = containsService.makeShoppingBasket(shoppingBasketBasketId);
        Contains contains = containsService.findContainsByShoppingBasket(shoppingBasket);
        ContainsDto findContainsDto = contains.toContainsDto();
        model.addAttribute("containsDto", findContainsDto);

        return "contains/containsUpdate";
    }

    @PostMapping("/contains/update/{shoppingBasketBasketId}")
    public String updateContains(@ModelAttribute ContainsDto containsDto, @PathVariable(name = "shoppingBasketBasketId") String shoppingBasketBasketId) {
        ShoppingBasket shoppingBasket = containsService.makeShoppingBasket(shoppingBasketBasketId);
        Contains contains = containsService.findContainsByShoppingBasket(shoppingBasket);
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
