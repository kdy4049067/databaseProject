package org.example.Author.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Author.domain.Author;
import org.example.Author.dto.AuthorDto;
import org.example.Author.service.AuthorService;
import org.example.Url.domain.Url;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/authorInsert")
    public String showAuthorInsertForm() {
        return "/author/authorInsert";
    }

    @PostMapping("/author/insert")
    public String manageAuthor(@ModelAttribute AuthorDto authorDto){
        String name=  authorDto.name();
        String urls = authorDto.urls();
        Url url = authorService.makeUrl(urls);

        Author author = new Author(name, urls, url);
        authorService.saveAuthor(author);

        return "redirect:/author";
    }

    @GetMapping("/author")
    public String selectAllAuthor(Model model){
        List<AuthorDto> authorDtos = authorService.findAllAuthor();
        model.addAttribute("author", authorDtos);

        return "author/authorSelect";
    }

    @GetMapping("/author/update/{name}")
    public String findUpdateAuthor(Model model, @PathVariable(name = "name") String name){
        Author author = authorService.getAuthor(name);
        AuthorDto findAuthorDto = author.toAuthorDto();
        model.addAttribute("authorDto", findAuthorDto);

        return "author/authorUpdate";
    }

    @PostMapping("/author/update/{name}")
    public String updateAuthor(@ModelAttribute AuthorDto authorDto, @PathVariable(name = "name") String name){
        Author author = authorService.getAuthor(name);
        AuthorDto updatedAuthorDto = authorService.updateAuthor(author, authorDto);
        if(updatedAuthorDto == null){
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/author";
    }

    @PostMapping("/author/delete/{name}")
    public String deleteBook(@PathVariable(name = "name") String name){
        authorService.deleteAuthor(name);

        return "redirect:/author";
    }

}
