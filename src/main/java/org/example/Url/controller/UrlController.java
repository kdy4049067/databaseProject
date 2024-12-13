package org.example.Url.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Url.domain.Url;
import org.example.Url.dto.UrlDto;
import org.example.Url.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class UrlController {  // BookController -> UrlController로 이름 변경

    private final UrlService urlService;  // BookService -> UrlService로 변경

    public UrlController(UrlService urlService){  // BookService -> UrlService로 변경
        this.urlService = urlService;
    }

    @GetMapping("/urlInsert")
    public String showUrlInsertForm() {
        // urlInsert.html 페이지로 이동
        return "/url/urlInsert";
    }

    @PostMapping("/url/insert")
    public String manageUrl(@ModelAttribute UrlDto urlDto){
        String url =  urlDto.url();
        String address = urlDto.address();

        Url urlEntity = new Url(url, address);
        urlService.insertUrl(urlEntity);

        return "redirect:/url";
    }

    @GetMapping("/url")
    public String selectAllUrl(Model model){
        List<UrlDto> urlDtos = urlService.findAllUrl();
        model.addAttribute("url", urlDtos);

        return "url/urlSelect";
    }

    @GetMapping("/url/update/{url}")
    public String findUpdateUrl(Model model, @PathVariable(name = "url") String url){
        Url urlEntity = urlService.findUrlByUrl(url);
        UrlDto findUrlDto = urlEntity.toUrlDto();
        model.addAttribute("urlDto", findUrlDto);

        return "url/urlUpdate";
    }

    @PostMapping("/url/update/{url}")
    public String updateUrl(@ModelAttribute UrlDto urlDto, @PathVariable(name = "url") String url){
        Url urlEntity = urlService.findUrlByUrl(url);
        UrlDto updatedUrlDto = urlService.updateUrl(urlEntity, urlDto);
        if(updatedUrlDto == null){
            throw new IllegalArgumentException("업데이트 실패");
        }
        return "redirect:/url";
    }

    @PostMapping("/url/delete/{url}")
    public String deleteUrl(@PathVariable(name = "url") String url){
        urlService.deleteUrl(url);

        return "redirect:/url";
    }

}
