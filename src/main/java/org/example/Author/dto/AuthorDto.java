package org.example.Author.dto;

import org.example.Award.Award;
import org.example.Book.domain.Book;
import org.example.Url.Url;

import java.net.URL;
import java.util.List;

public record AuthorDto(String name, Url url, List<Award> awards, List<Book> books) {
}
