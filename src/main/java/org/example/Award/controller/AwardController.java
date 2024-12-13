package org.example.Award.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Author.domain.Author;
import org.example.Award.domain.Award;
import org.example.Award.dto.AwardDto;
import org.example.Award.service.AwardService;
import org.example.Book.domain.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class AwardController {

    private final AwardService awardService;

    public AwardController(AwardService awardService){
        this.awardService = awardService;
    }

    @GetMapping("/awardInsert")
    public String showAwardInsertForm() {
        return "/award/awardInsert";
    }

    @PostMapping("/award/insert")
    public String manageAward(@ModelAttribute AwardDto awardDto){
        String name = awardDto.name();
        String year = awardDto.year();
        String authorName = awardDto.authorName();
        String bookIsbn = awardDto.bookIsbn();
        Book book = awardService.makeBook(bookIsbn);
        Author author = awardService.makeAuthor(authorName);

        Award award = new Award(name, year, author, book);
        awardService.insertAward(award);

        return "redirect:/award";
    }

    @GetMapping("/award")
    public String selectAllAward(Model model){
        List<AwardDto> awardDtos = awardService.findAllAward();
        model.addAttribute("award", awardDtos);

        return "award/awardSelect";
    }

    @GetMapping("/award/update/{name}")
    public String findUpdateAward(Model model, @PathVariable(name = "name") String name){
        Award award = awardService.findAwardByName(name);
        AwardDto findAwardDto = award.toAwardDto();
        model.addAttribute("awardDto", findAwardDto);

        return "award/awardUpdate";
    }

    @PostMapping("/award/update/{name}")
    public String updateAward(@ModelAttribute AwardDto awardDto, @PathVariable(name = "name") String name){
        Award award = awardService.findAwardByName(name);
        AwardDto updatedAwardDto = awardService.updateAward(award, awardDto);
        if(updatedAwardDto == null){
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/award";
    }

    @PostMapping("/award/delete/{name}")
    public String deleteAward(@PathVariable(name = "name") String name){
        awardService.deleteAward(name);

        return "redirect:/award";
    }

}

