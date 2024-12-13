package org.example.PhoneCustomer.repository;

import org.example.Book.domain.Book;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneCustomerRepository extends JpaRepository<PhoneCustomer, String> {

    public boolean existsByPhone(String phone);
    public PhoneCustomer findPhoneCustomerByPhone(String phone);
    public void deletePhoneCustomerByPhone(String phone);

}
