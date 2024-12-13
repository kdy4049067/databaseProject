package org.example.Award.dto;

import org.example.Author.domain.Author;

public record AwardDto(String name, String year, String authorName, String bookIsbn) {
}
