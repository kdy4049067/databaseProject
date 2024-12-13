package org.example.WareHouse.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Book.domain.Book;
import org.example.Inventory.domain.Inventory;
import org.example.WareHouse.dto.WareHouseDto;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WareHouse {

    @Id
    private String code;
    @Column
    private String address;
    @Column
    private String phone;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    @OneToMany(mappedBy = "warehouse")
    private List<Inventory> inventories;

    public WareHouse(String code, String address, String phone) {
        this.code = code;
        this.address = address;
        this.phone = phone;
    }

    public WareHouseDto toWareHouseDto(){
        return new WareHouseDto(
                this.code,
                this.address,
                this.phone);
    }

}
