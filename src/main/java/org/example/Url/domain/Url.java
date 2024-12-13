package org.example.Url.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.example.Author.domain.Author;
import org.example.Book.dto.BookDto;
import org.example.Url.dto.UrlDto;
import org.hibernate.annotations.Cache;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Url {

    @Id
    private String url;
    @Column
    private String address;

    @OneToOne(mappedBy = "url")
    private Author author;

    public Url(String url, String address) {
        this.url = url;
        this.address = address;
    }

    public UrlDto toUrlDto(){
        return new UrlDto(
                this.url,
                this.address);
    }

}

