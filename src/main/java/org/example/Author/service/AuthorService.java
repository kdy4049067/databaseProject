package org.example.Author.service;

import jakarta.transaction.Transactional;
import org.example.Author.domain.Author;
import org.example.Author.dto.AuthorDto;
import org.example.Author.repository.AuthorRepository;
import org.example.Customer.domain.Customer;
import org.example.PhoneCustomer.domain.PhoneCustomer;
import org.example.Url.domain.Url;
import org.example.Url.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UrlRepository urlRepository;

    public Url makeUrl(String urls){
        return urlRepository.findUrlByUrl(urls);
    }

    public AuthorDto saveAuthor(Author author) {
        Author newAuthor = authorRepository.save(author);
        return newAuthor.toAuthorDto();
    }

    public Author getAuthor(String name) {
        return authorRepository.findAuthorByName(name);
    }

    public List<AuthorDto> findAllAuthor(){
        List<Author> author = authorRepository.findAll();
        return author.stream()
                .map(Author::toAuthorDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorDto updateAuthor(Author author, AuthorDto newAuthorDto) {
        if (!author.getName().equals(newAuthorDto.name()) && isNameExist(newAuthorDto.name())) {
            throw new IllegalArgumentException("이미 존재하는 name입니다.");
        }
        Url url = urlRepository.findUrlByUrl(newAuthorDto.urls());
        author.setUrl(url);
        author.setName(newAuthorDto.name());
        author.setUrls(newAuthorDto.urls());

        return author.toAuthorDto();
    }

    public void deleteAuthor(String name) {
        authorRepository.deleteAuthorByName(name);
    }

    public boolean isNameExist(String name) {
        return authorRepository.existsByName(name);
    }

}
