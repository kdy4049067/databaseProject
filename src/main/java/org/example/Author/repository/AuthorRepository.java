package org.example.Author.repository;

import org.example.Author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    public Author findAuthorByName(String name);
    public void deleteAuthorByName(String name);
}
