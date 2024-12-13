package org.example.Award.repository;

import org.example.Award.domain.Award;
import org.example.Book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, String> {

    public boolean existsByName(String name);
    public Award findAwardByName(String name);
    public void deleteAwardByName(String name);

}
