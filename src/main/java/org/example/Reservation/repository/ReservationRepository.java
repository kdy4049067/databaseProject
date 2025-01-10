package org.example.Reservation.repository;

import org.example.Book.domain.Book;
import org.example.Customer.domain.Customer;
import org.example.Reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    public Reservation findReservationById(Long id);
    boolean existsByCustomerAndBooksContaining(Customer customer, Book book);
    Reservation findByCustomer(Customer customer);
    Reservation findReservationByCustomer(Customer customer);
    boolean existsByPickupTimeBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
