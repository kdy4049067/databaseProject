package org.example.Reservation.service;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
import org.example.Customer.domain.Customer;
import org.example.Customer.repository.CustomerRepository;
import org.example.Reservation.domain.Reservation;
import org.example.Reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Reservation findReservation(Customer customer){
        return reservationRepository.findByCustomer(customer);
    }

    public Reservation findReservationById(Long id){
        return reservationRepository.findReservationById(id);
    }

    @Transactional
    public void reserveBooks(String email, String isbn, LocalDateTime pickupTime) {

        Customer customer = customerRepository.findByEmail(email);

        Book book = bookRepository.findBookByIsbn(isbn);

        boolean isTimeConflict = reservationRepository.existsByPickupTimeBetween(
                pickupTime.minusMinutes(10), pickupTime.plusMinutes(10));
        if (isTimeConflict) {
            throw new IllegalArgumentException("There is already a reservation within 10 minutes of the selected pickup time.");
        }

        boolean hasAlreadyReserved = reservationRepository.existsByCustomerAndBooksContaining(customer, book);
        if (hasAlreadyReserved) {
            throw new IllegalArgumentException("This book has already been reserved by the customer.");
        }

        Reservation reservation = customer.getReservation();
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setPickupTime(pickupTime);
        reservation.getBooks().add(book);

        if (!book.getReservations().contains(reservation)) {
            book.getReservations().add(reservation);
        }

        bookRepository.save(book);
        reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long reservationId, String title) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));

        Book cancelBook = bookRepository.findBookByTitle(title);
        List<Book> reservedBooks = new ArrayList<>(reservation.getBooks());

        for (Book book : reservedBooks) {
            if (book.equals(cancelBook)) {
                book.getContains().setNumber(book.getContains().getNumber() + 1);

                book.getReservations().remove(reservation);
                reservation.getBooks().remove(book);

                bookRepository.save(book);
            }
        }

        reservationRepository.save(reservation);
    }


    @Transactional
    public void updatePickupTime(Long reservationId, LocalDateTime newPickupTime) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));

        boolean isTimeConflict = reservationRepository.existsByPickupTimeBetween(
                newPickupTime.minusMinutes(10), newPickupTime.plusMinutes(10));
        if (isTimeConflict) {
            throw new IllegalArgumentException("There is already a reservation within 10 minutes of the new pickup time.");
        }

        reservation.setPickupTime(newPickupTime);
        reservationRepository.save(reservation);
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }


}

