package org.example.Reservation.repository;

import org.example.Customer.domain.Customer;
import org.example.Reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findReservationByCustomer(Customer customer);
}
