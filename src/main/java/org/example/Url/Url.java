package org.example.Url;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.example.Author.domain.Author;

@Entity
public class Url {

    @Id
    private String url;

    private String address;

    @OneToOne(mappedBy = "url")
    private Author author;

}

