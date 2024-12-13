package org.example.Customer;

import jakarta.persistence.*;
import org.example.PhoneCustomer.PhoneCustomer;
import org.example.Reservation.Reservation;

import java.util.List;

@Entity
public class Customer {

    @Id
    private String email;

    private String phone;

    @OneToOne
    @JoinColumn(name = "phoneCustomer_Phone")  // `customer_phone` 테이블의 `phone` 참조
    private PhoneCustomer phoneCustomer;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

}
