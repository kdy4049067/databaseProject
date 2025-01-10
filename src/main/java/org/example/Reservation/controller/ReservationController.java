package org.example.Reservation.controller;

import jakarta.persistence.Column;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
import org.example.Customer.domain.Customer;
import org.example.Customer.service.CustomerService;
import org.example.Reservation.domain.Reservation;
import org.example.Reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/reservation/{email}")
    public String viewReservations(@PathVariable("email") String email, Model model) {
        Customer customer = customerService.findCustomerByEmail(email);
        Reservation reservation = reservationService.findReservation(customer);
        List<Book> books = reservation.getBooks();
        model.addAttribute("reservations", reservation);
        model.addAttribute("books", books);
        return "reservation-list";
    }


    @PostMapping("/customer/{email}/book/reserve")
    public String reserveBook(@PathVariable("email") String email,
                              @RequestParam(name="isbn") String isbn,
                              @RequestParam(name="pickupTime") String pickupTime,
                              Model model) {
        LocalDateTime pickupDateTime = LocalDateTime.parse(pickupTime);

        try {
            reservationService.reserveBooks(email, isbn, pickupDateTime);
            return "redirect:/reservation/" + email;
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "이미 존재하는 Reservation입니다.");
            return "error";
        }
    }

    @PostMapping("/reservation/{id}/{title}/cancel")
    public String cancelReservation(@PathVariable("id") Long id,
                                    @PathVariable("title")String title,
                                    Model model) {
        Reservation reservation = reservationService.findReservationById(id);
        Customer customer = reservation.getCustomer();
        try {
            reservationService.cancelReservation(id, title);
            return "redirect:/reservation/" + customer.getEmail();
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "cancle 오류");
            return "error";
        }
    }

    @PostMapping("/reservation/{id}/update")
    public String updatePickupTime(@PathVariable("id") Long id,
                                   @RequestParam(name="newPickupTime") String newPickupTime,
                                   Model model) {
        LocalDateTime newTime = LocalDateTime.parse(newPickupTime);
        Reservation reservation = reservationService.findReservationById(id);
        Customer customer = reservation.getCustomer();
        try {
            reservationService.updatePickupTime(id, newTime);
            return "redirect:/reservation/" + customer.getEmail();
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "업데이트 실패.");
            return "error";
        }
    }

}

