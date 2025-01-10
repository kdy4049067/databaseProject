package org.example.Customer.repository;

import org.example.Book.domain.Book;
import org.example.Customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    public boolean existsByEmail(String email);

    public Customer findByEmail(String email);

    public void deleteCustomerByEmail(String email);

}
