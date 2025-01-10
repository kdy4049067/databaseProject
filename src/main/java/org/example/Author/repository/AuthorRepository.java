package org.example.Author.repository;

import org.example.Author.domain.Author;
import org.example.Url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    public Author findAuthorByName(String name);
    public List<Author> findAuthorsByName(String authorName);
    public void deleteAuthorByName(String name);
    public boolean existsByName(String name);
    public Author findAuthorByUrl(Url url);
}
