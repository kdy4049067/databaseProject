package org.example.PhoneCustomer.service;

import jakarta.transaction.Transactional;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.PhoneCustomer.dto.PhoneCustomerDto;
import org.example.PhoneCustomer.repository.PhoneCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneCustomerService {

    private final PhoneCustomerRepository phoneCustomerRepository;

    public PhoneCustomerService(PhoneCustomerRepository phoneCustomerRepository){
        this.phoneCustomerRepository = phoneCustomerRepository;
    }

    @Transactional
    public PhoneCustomerDto insertPhoneCustomer(PhoneCustomer phoneCustomer){
        PhoneCustomer newPhoneCustomer = phoneCustomerRepository.save(phoneCustomer);
        return newPhoneCustomer.toPhoneCustomerDto();
    }

    public List<PhoneCustomerDto> findAllPhoneCustomer(){
        List<PhoneCustomer> phoneCustomer = phoneCustomerRepository.findAll();
        return phoneCustomer.stream()
                .map(PhoneCustomer::toPhoneCustomerDto)
                .collect(Collectors.toList());
    }

    public PhoneCustomer findPhoneCustomerByPhone(String phone){
        return phoneCustomerRepository.findPhoneCustomerByPhone(phone);
    }

    @Transactional
    public PhoneCustomerDto updatePhoneCustomer(PhoneCustomer phoneCustomer, PhoneCustomerDto phoneCustomerDto){
        if (!phoneCustomer.getPhone().equals(phoneCustomerDto.phone()) && isPhoneExist(phoneCustomerDto.phone())) {
            throw new IllegalArgumentException("이미 존재하는 phone입니다.");
        }

        phoneCustomer.setPhone(phoneCustomerDto.phone());
        phoneCustomer.setAddress(phoneCustomerDto.address());
        phoneCustomer.setName(phoneCustomerDto.name());

        return phoneCustomer.toPhoneCustomerDto();
    }

    public boolean isPhoneExist(String phone) {
        return phoneCustomerRepository.existsByPhone(phone);
    }

    @Transactional
    public void deletePhoneCustomer(String phone) {
        phoneCustomerRepository.deletePhoneCustomerByPhone(phone);
    }
}
