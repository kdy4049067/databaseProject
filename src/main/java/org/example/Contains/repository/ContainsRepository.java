package org.example.Contains.repository;

import org.example.Book.domain.Book;
import org.example.Contains.domain.Contains;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainsRepository extends JpaRepository<Contains, Long> {
    public Contains findContainsByBook(Book book);
    public boolean existsContainsByBook(Book book);
    public void deleteContainsByBook(Book book);
}
