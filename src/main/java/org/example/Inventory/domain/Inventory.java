package org.example.Inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Book.domain.Book;
import org.example.Inventory.dto.InventoryDto;
import org.example.WareHouse.domain.WareHouse;

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

    @OneToOne
    @JoinColumn(name = "book_isbn")
    private Book book;

    public Inventory(int number, Book book, WareHouse warehouse) {
        this.number = number;
        this.book = book;
        this.warehouse = warehouse;
    }

    public InventoryDto toInventoryDto(){
        return new InventoryDto(
                this.number,
                this.book.getIsbn(),
                this.warehouse.getCode());
    }

}
