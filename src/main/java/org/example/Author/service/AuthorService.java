package org.example.Author.service;

import org.example.Author.domain.Author;
import org.example.Author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    // Author 저장 (Create)
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Author 조회 (Read)
    public Author getAuthor(String name) {
        return authorRepository.findAuthorByName(name);
    }

    // Author 수정 (Update)
    public Author updateAuthor(String name, Author newAuthor) {
        Optional<Author> existingAuthor = authorRepository.findById(name);
        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            author.setUrl(newAuthor.getUrl());
            author.setAwards(newAuthor.getAwards());
            author.setBooks(newAuthor.getBooks());
            return authorRepository.save(author);
        }
        return null; // 혹은 예외 처리
    }

    // Author 삭제 (Delete)
    public void deleteAuthor(String name) {
        authorRepository.deleteAuthorByName(name);
    }
}
