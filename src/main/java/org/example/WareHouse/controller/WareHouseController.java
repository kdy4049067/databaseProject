package org.example.WareHouse.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.WareHouse.domain.WareHouse;
import org.example.WareHouse.dto.WareHouseDto;
import org.example.WareHouse.service.WareHouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class WareHouseController {

    private final WareHouseService wareHouseService;

    public WareHouseController(WareHouseService wareHouseService){
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("/wareHouseInsert")
    public String showWareHouseInsertForm() {
        return "/wareHouse/wareHouseInsert";
    }

    @PostMapping("/wareHouse/insert")
    public String manageWareHouse(@ModelAttribute WareHouseDto wareHouseDto){
        String code = wareHouseDto.code();
        String address = wareHouseDto.address();
        String phone = wareHouseDto.phone();

        WareHouse wareHouse = new WareHouse(code, address, phone);
        wareHouseService.insertWareHouse(wareHouse);

        return "redirect:/wareHouse";
    }

    @GetMapping("/wareHouse")
    public String selectAllWareHouse(Model model){
        List<WareHouseDto> wareHouseDtos = wareHouseService.findAllWareHouse();
        model.addAttribute("wareHouse", wareHouseDtos);

        return "wareHouse/wareHouseSelect";
    }

    @GetMapping("/wareHouse/update/{code}")
    public String findUpdateWareHouse(Model model, @PathVariable(name = "code") String code){
        WareHouse wareHouse = wareHouseService.findWareHouseByCode(code);
        WareHouseDto findWareHouseDto = wareHouse.toWareHouseDto();
        model.addAttribute("wareHouseDto", findWareHouseDto);

        return "wareHouse/wareHouseUpdate";
    }

    @PostMapping("/wareHouse/update/{code}")
    public String updateWareHouse(@ModelAttribute WareHouseDto wareHouseDto, @PathVariable(name = "code") String code){
        WareHouse wareHouse = wareHouseService.findWareHouseByCode(code);
        WareHouseDto updatedWareHouseDto = wareHouseService.updateWareHouse(wareHouse, wareHouseDto);
        if(updatedWareHouseDto == null){
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/wareHouse";
    }

    @PostMapping("/wareHouse/delete/{code}")
    public String deleteWareHouse(@PathVariable(name = "code") String code){
        wareHouseService.deleteWareHouse(code);

        return "redirect:/wareHouse";
    }

}
