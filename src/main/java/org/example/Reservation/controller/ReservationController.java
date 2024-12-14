package org.example.Reservation.controller;

import jakarta.persistence.Column;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
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

    @GetMapping("/customer/{email}/book/showAllBooks")
    public String showAllBooks(@PathVariable String email, Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("email", email);
        return "book-list";
    }

    @PostMapping("/customer/{email}/book/reserve")
    public String reserveBook(@PathVariable String email,
                              @RequestParam String isbn,
                              @RequestParam String pickupTime) {
        LocalDateTime pickupDateTime = LocalDateTime.parse(pickupTime);

        try {
            reservationService.reserveBooks(email, isbn, pickupDateTime);
            return "redirect:/customer/" + email + "/reservations";
        } catch (IllegalArgumentException e) {
            return "error";
        }
    }


}

