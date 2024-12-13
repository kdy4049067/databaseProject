package org.example.PhoneCustomer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Customer.domain.Customer;
import org.example.PhoneCustomer.dto.PhoneCustomerDto;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhoneCustomer {

    @Id
    private String phone;
    @Column
    private String address;
    @Column
    private String name;
    @OneToOne(mappedBy = "phoneCustomer")
    private Customer customer;

    public PhoneCustomer(String phone, String address, String name) {
        this.phone = phone;
        this.address = address;
        this.name = name;
    }

    public PhoneCustomerDto toPhoneCustomerDto(){
        return new PhoneCustomerDto(
                this.phone,
                this.address,
                this.name);
    }

}
