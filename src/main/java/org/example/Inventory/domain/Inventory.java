package org.example.Inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Book.domain.Book;
import org.example.Inventory.dto.InventoryDto;
import org.example.WareHouse.domain.WareHouse;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int number;

    @ManyToOne
    @JoinColumn(name = "warehouse_code")
    private WareHouse warehouse;

    @OneToMany(mappedBy = "inventory")
    private List<Book> books;

    public Inventory(int number, WareHouse warehouse) {
        this.number = number;
        this.warehouse = warehouse;
    }

    public InventoryDto toInventoryDto(){
        return new InventoryDto(
                this.number,
                this.warehouse.getCode());
    }

}
