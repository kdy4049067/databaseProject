package org.example.Url.service;

import jakarta.transaction.Transactional;
import org.example.Author.domain.Author;
import org.example.Author.repository.AuthorRepository;
import org.example.Book.domain.Book;
import org.example.Book.dto.BookDto;
import org.example.Book.repository.BookRepository;
import org.example.Url.domain.Url;
import org.example.Url.dto.UrlDto;
import org.example.Url.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final AuthorRepository authorRepository;

    public UrlService(UrlRepository urlRepository, AuthorRepository authorRepository){
        this.urlRepository = urlRepository;
        this.authorRepository = authorRepository;
    }

    public UrlDto insertUrl(Url url){
        Url newUrl = urlRepository.save(url);
        return newUrl.toUrlDto();
    }

    public List<UrlDto> findAllUrl(){
        List<Url> url = urlRepository.findAll();
        return url.stream()
                .map(Url::toUrlDto)
                .collect(Collectors.toList());
    }

    public Url findUrlByUrl(String url){
        return urlRepository.findUrlByUrl(url);
    }

    @Transactional
    public UrlDto updateUrl(Url url, UrlDto urlDto){
        if (!url.getUrl().equals(urlDto.url()) && isUrlExist(urlDto.url())) {
            throw new IllegalArgumentException("이미 존재하는 url입니다.");
        }
        url.setAddress(urlDto.address());
        return url.toUrlDto();
    }

    public boolean isUrlExist(String url) {
        return urlRepository.existsByUrl(url);
    }

    @Transactional
    public void deleteUrl(String url) {
        urlRepository.deleteUrlByUrl(url);
    }

}
