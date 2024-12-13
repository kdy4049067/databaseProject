package org.example.Author.dto;

import org.example.Award.domain.Award;
import org.example.Book.domain.Book;
import org.example.Url.domain.Url;

import java.util.List;

public record AuthorDto(String name, String urls, Url url) {
}
