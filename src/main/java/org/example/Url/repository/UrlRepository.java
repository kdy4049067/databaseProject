package org.example.Url.repository;

import org.example.Book.domain.Book;
import org.example.Url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {

    public boolean existsByUrl(String url);
    public Url findUrlByUrl(String url);
    public void deleteUrlByUrl(String url);

}
