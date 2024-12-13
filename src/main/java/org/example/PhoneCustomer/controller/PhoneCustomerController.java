package org.example.PhoneCustomer.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.PhoneCustomer.dto.PhoneCustomerDto;
import org.example.PhoneCustomer.service.PhoneCustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class PhoneCustomerController {

    private final PhoneCustomerService phoneCustomerService;

    public PhoneCustomerController(PhoneCustomerService phoneCustomerService) {
        this.phoneCustomerService = phoneCustomerService;
    }

    @GetMapping("/phoneCustomerInsert")
    public String showPhoneCustomerInsertForm() {
        return "/phoneCustomer/phoneCustomerInsert";
    }

    @PostMapping("/phoneCustomer/insert")
    public String managePhoneCustomer(@ModelAttribute PhoneCustomerDto phoneCustomerDto) {
        String phone = phoneCustomerDto.phone();
        String address = phoneCustomerDto.address();
        String name = phoneCustomerDto.name();

        PhoneCustomer phoneCustomer = new PhoneCustomer(phone, address, name);
        phoneCustomerService.insertPhoneCustomer(phoneCustomer);

        return "redirect:/phoneCustomer";
    }

    @GetMapping("/phoneCustomer")
    public String selectAllPhoneCustomer(Model model) {
        List<PhoneCustomerDto> phoneCustomerDtos = phoneCustomerService.findAllPhoneCustomer();
        model.addAttribute("phoneCustomer", phoneCustomerDtos);

        return "phoneCustomer/phoneCustomerSelect";
    }

    @GetMapping("/phoneCustomer/update/{phone}")
    public String findUpdatePhoneCustomer(Model model, @PathVariable(name = "phone") String phone) {
        PhoneCustomer phoneCustomer = phoneCustomerService.findPhoneCustomerByPhone(phone);
        PhoneCustomerDto findPhoneCustomerDto = phoneCustomer.toPhoneCustomerDto();
        model.addAttribute("phoneCustomerDto", findPhoneCustomerDto);

        return "phoneCustomer/phoneCustomerUpdate";
    }

    @PostMapping("/phoneCustomer/update/{phone}")
    public String updatePhoneCustomer(@ModelAttribute PhoneCustomerDto phoneCustomerDto, @PathVariable(name = "phone") String phone) {
        PhoneCustomer phoneCustomer = phoneCustomerService.findPhoneCustomerByPhone(phone);
        PhoneCustomerDto updatedPhoneCustomerDto = phoneCustomerService.updatePhoneCustomer(phoneCustomer, phoneCustomerDto);
        if (updatedPhoneCustomerDto == null) {
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/phoneCustomer";
    }

    @PostMapping("/phoneCustomer/delete/{phone}")
    public String deletePhoneCustomer(@PathVariable(name = "phone") String phone) {
        phoneCustomerService.deletePhoneCustomer(phone);

        return "redirect:/phoneCustomer";
    }
}
