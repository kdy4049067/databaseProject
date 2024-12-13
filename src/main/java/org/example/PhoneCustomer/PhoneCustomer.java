package org.example.PhoneCustomer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.example.Customer.Customer;

@Entity
public class PhoneCustomer {

    @Id
    private String phone;  // 기본키
    private String address;
    private String name;
    @OneToOne(mappedBy = "phoneCustomer") // 'Customer'에서 참조하는 'phoneCustomer'를 매핑
    private Customer customer;

}
