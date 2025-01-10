package org.example.Award.service;

import jakarta.transaction.Transactional;
import org.example.Author.domain.Author;
import org.example.Author.repository.AuthorRepository;
import org.example.Award.domain.Award;
import org.example.Award.dto.AwardDto;
import org.example.Award.repository.AwardRepository;
import org.example.Book.domain.Book;
import org.example.Book.repository.BookRepository;
import org.example.Url.domain.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwardService {

    private final AuthorRepository authorRepository;
    private final AwardRepository awardRepository;
    private final BookRepository bookRepository;

    public AwardService(AuthorRepository authorRepository, AwardRepository awardRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.awardRepository = awardRepository;
        this.bookRepository = bookRepository;
    }

    public Author makeAuthor(String authorName){
        return authorRepository.findAuthorByName(authorName);
    }

    public Book makeBook(String bookIsbn){
        return bookRepository.findBookByIsbn(bookIsbn);
    }

    @Transactional
    public AwardDto insertAward(Award award){
        Award newAuthor = awardRepository.save(award);
        return newAuthor.toAwardDto();
    }

    public List<AwardDto> findAllAward(){
        List<Award> awards = awardRepository.findAll();
        return awards.stream()
                .map(Award::toAwardDto)
                .collect(Collectors.toList());
    }

    public Award findAwardByName(String name){
        return awardRepository.findAwardByName(name);
    }

    @Transactional
    public AwardDto updateAward(Award award, AwardDto awardDto){
        if (!award.getName().equals(awardDto.name()) && isNameExist(awardDto.name())) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
        Author author = authorRepository.findAuthorByName(awardDto.authorName());
        Book book = makeBook(awardDto.bookIsbn());

        award.setBook(book);
        award.setAuthor(author);
        award.setName(awardDto.name());
        award.setYear(awardDto.year());

        return award.toAwardDto();
    }

    public boolean isNameExist(String name) {
        return awardRepository.existsByName(name);
    }

    @Transactional
    public void deleteAward(String name) {
        awardRepository.deleteAwardByName(name);
    }
}
