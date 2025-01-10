package org.example.Customer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Customer.dto.CustomerDto;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.Reservation.domain.Reservation;
import org.example.ShoppingBasket.ShoppingBasket;

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
    @JoinColumn(name = "phoneCustomer_Phone")
    private PhoneCustomer phoneCustomer;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Reservation reservation;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private ShoppingBasket shoppingBasket;

    public Customer(String email, String phone, PhoneCustomer phoneCustomer) {
        this.email = email;
        this.phone = phone;
        this.phoneCustomer = phoneCustomer;
    }


    public CustomerDto toCustomerDto(){
        return new CustomerDto(
                this.email,
                this.phone,
                this.phoneCustomer.getPhone());
    }

}
