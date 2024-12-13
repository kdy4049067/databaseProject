package org.example.Customer.service;

import jakarta.transaction.Transactional;
import org.example.Customer.domain.Customer;
import org.example.Customer.dto.CustomerDto;
import org.example.Customer.repository.CustomerRepository;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.PhoneCustomer.repository.PhoneCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private final PhoneCustomerRepository phoneCustomerRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(PhoneCustomerRepository phoneCustomerRepository, CustomerRepository customerRepository){
        this.phoneCustomerRepository = phoneCustomerRepository;
        this.customerRepository = customerRepository;
    }

    public PhoneCustomer makePhoneCustomer(String phone){
        return phoneCustomerRepository.findPhoneCustomerByPhone(phone);
    }

    public CustomerDto insertCustomer(Customer customer){
        PhoneCustomer phoneCustomer = phoneCustomerRepository.findPhoneCustomerByPhone(customer.getPhone());
        customer.setPhoneCustomer(phoneCustomer);
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer.toCustomerDto();
    }

    public List<CustomerDto> findAllCustomer(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(Customer::toCustomerDto)
                .collect(Collectors.toList());
    }

    public Customer findCustomerByEmail(String email){
        return customerRepository.findCustomerByEmail(email);
    }

    @Transactional
    public CustomerDto updateCustomer(Customer customer, CustomerDto customerDto){
        if (!customer.getEmail().equals(customerDto.email()) && isEmailExist(customerDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 email입니다.");
        }

        PhoneCustomer phoneCustomer = phoneCustomerRepository.findPhoneCustomerByPhone(customerDto.phone());
        customer.setEmail(customerDto.email());
        customer.setPhone(customerDto.phone());
        customer.setPhoneCustomer(phoneCustomer);

        return customer.toCustomerDto();
    }

    public boolean isEmailExist(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Transactional
    public void deleteCustomer(String email) {
        customerRepository.deleteCustomerByEmail(email);
    }

}

