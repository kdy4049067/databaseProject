package org.example.WareHouse.service;

import jakarta.transaction.Transactional;
import org.example.WareHouse.domain.WareHouse;
import org.example.WareHouse.dto.WareHouseDto;
import org.example.WareHouse.repository.WareHouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WareHouseService {

    private final WareHouseRepository wareHouseRepository;

    public WareHouseService(WareHouseRepository wareHouseRepository){
        this.wareHouseRepository = wareHouseRepository;
    }

    @Transactional
    public WareHouseDto insertWareHouse(WareHouse wareHouse){
        WareHouse newWareHouse = wareHouseRepository.save(wareHouse);
        return newWareHouse.toWareHouseDto();
    }

    public List<WareHouseDto> findAllWareHouse(){
        List<WareHouse> wareHouse = wareHouseRepository.findAll();
        return wareHouse.stream()
                .map(WareHouse::toWareHouseDto)
                .collect(Collectors.toList());
    }

    public WareHouse findWareHouseByCode(String code){
        return wareHouseRepository.findWareHouseByCode(code);
    }

    @Transactional
    public WareHouseDto updateWareHouse(WareHouse wareHouse, WareHouseDto wareHouseDto){
        if (!wareHouse.getCode().equals(wareHouseDto.code()) && isCodeExist(wareHouseDto.code())) {
            throw new IllegalArgumentException("이미 존재하는 code입니다.");
        }

        wareHouse.setCode(wareHouseDto.code());
        wareHouse.setAddress(wareHouseDto.address());
        wareHouse.setPhone(wareHouseDto.phone());

        return wareHouse.toWareHouseDto();
    }

    public boolean isCodeExist(String code) {
        return wareHouseRepository.existsByCode(code);
    }

    @Transactional
    public void deleteWareHouse(String code) {
        wareHouseRepository.deleteWareHouseByCode(code);
    }

}
