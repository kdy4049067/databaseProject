package org.example.WareHouse.repository;

import org.example.Book.domain.Book;
import org.example.WareHouse.domain.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WareHouseRepository extends JpaRepository<WareHouse, String> {

    public boolean existsByCode(String code);
    public WareHouse findWareHouseByCode(String code);
    public void deleteWareHouseByCode(String code);

}
