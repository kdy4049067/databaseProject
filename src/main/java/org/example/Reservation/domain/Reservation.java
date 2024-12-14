package org.example.Reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Book.domain.Book;
import org.example.Customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    private Long id;
    @Column
    private LocalDateTime reservationDate;
    @Column
    private LocalDateTime pickupTime;

    @ManyToMany(mappedBy = "reservations")
    private List<Book> books;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;

}
