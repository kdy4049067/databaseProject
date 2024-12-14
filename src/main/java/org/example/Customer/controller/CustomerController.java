package org.example.Customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Book.domain.Book;
import org.example.Customer.domain.Customer;
import org.example.Customer.dto.CustomerDto;
import org.example.Customer.service.CustomerService;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.ShoppingBasket.ShoppingBasket;
import org.example.User.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/customerInsert")
    public String showCustomerInsertForm() {
        return "/customer/customerInsert";
    }

    @PostMapping("/customer/insert")
    public String manageCustomer(@ModelAttribute CustomerDto customerDto){
        String email = customerDto.email();
        String phone = customerDto.phone();
        PhoneCustomer phoneCustomer = customerService.makePhoneCustomer(phone);

        Customer customer = new Customer(email, phone, phoneCustomer);
        customerService.insertCustomer(customer);

        return "redirect:/customer";
    }

    @GetMapping("/customer")
    public String selectAllCustomer(Model model){
        List<CustomerDto> customerDtos = customerService.findAllCustomer();
        model.addAttribute("customer", customerDtos);

        return "customer/customerSelect";
    }

    @GetMapping("/customer/update/{email}")
    public String findUpdateCustomer(Model model, @PathVariable(name = "email") String email){
        Customer customer = customerService.findCustomerByEmail(email);
        CustomerDto findCustomerDto = customer.toCustomerDto();
        model.addAttribute("customerDto", findCustomerDto);

        return "customer/customerUpdate";
    }

    @PostMapping("/customer/update/{email}")
    public String updateCustomer(@ModelAttribute CustomerDto customerDto, @PathVariable(name = "email") String email){
        Customer customer = customerService.findCustomerByEmail(email);
        CustomerDto updatedCustomerDto = customerService.updateCustomer(customer, customerDto);
        if(updatedCustomerDto == null){
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/customer";
    }

    @PostMapping("/customer/delete/{email}")
    public String deleteCustomer(@PathVariable(name = "email") String email){
        customerService.deleteCustomer(email);

        return "redirect:/customer";
    }

    @PostMapping("/customer/author/search")
    public String searchByAuthorName(@RequestBody String authorName, Model model){
        List<Book> books = customerService.findBooksByAuthorName(authorName);
        model.addAttribute("books", books);

        return "findBooksByAuthor";
    }

    @PostMapping("/customer/award/search")
    public String searchByAwardName(@RequestBody String awardName, Model model){
        List<Book> books = customerService.findBooksByAwardName(awardName);
        model.addAttribute("books", books);

        return "findBooksByAward";
    }

    @PostMapping("/customer/book/search")
    public String searchByBookTitle(@RequestBody String title, Model model){
        List<Book> books = customerService.findBooksByTitle(title);
        model.addAttribute("books", books);

        return "findBooksByTitle";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute CustomerDto customerDto, Model model) {
        boolean isAuthenticated = customerService.login(customerDto.email(), customerDto.phoneCustomer().getPhone());

        if(!isAuthenticated) {
            model.addAttribute("error","login failed");
            return "index";
        }
        model.addAttribute("email", customerDto.email());
        return "customer-home";
    }

    @GetMapping("/customer/{email}/shopping-basket")
    public String viewShoppingBasket(@PathVariable String email, Model model){
        ShoppingBasket shoppingBasket = customerService.findMyShoppingBasket(email);
        model.addAttribute("shoppingBasket", shoppingBasket);
        return "customer-shoppingBasket";
    }


}
