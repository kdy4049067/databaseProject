package org.example.Reservation;

import jakarta.persistence.*;
import org.example.Book.domain.Book;
import org.example.Customer.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reservation {

    @Id
    private Long id;  // 기본키

    private LocalDateTime reservationDate;
    private LocalDateTime pickupTime;

    @ManyToMany(mappedBy = "reservations") // `Book` 엔티티에서 `reservations` 필드를 통해 관계를 정의하므로 `mappedBy` 사용
    private List<Book> books;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_email")  // `customer` 테이블의 `email` 참조
    private Customer customer;

    // 기본 생성자, getter, setter
}
