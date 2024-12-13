package org.example.Customer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Customer.dto.CustomerDto;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.Reservation.Reservation;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    private String email;
    @Column
    private String phone;

    @OneToOne
    @JoinColumn(name = "phoneCustomer_Phone")  // `customer_phone` 테이블의 `phone` 참조
    private PhoneCustomer phoneCustomer;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    public Customer(String email, String phone, PhoneCustomer phoneCustomer) {
        this.email = email;
        this.phone = phone;
        this.phoneCustomer = phoneCustomer;
    }


    public CustomerDto toCustomerDto(){
        return new CustomerDto(
                this.email,
                this.phone);
    }

}
