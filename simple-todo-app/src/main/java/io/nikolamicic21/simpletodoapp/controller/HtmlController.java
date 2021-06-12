package io.nikolamicic21.simpletodoapp.controller;

import io.nikolamicic21.simpletodoapp.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
public class HtmlController {

    private final ImageService imageService;

    @GetMapping
    public String getIndexPage(Model model) {
        final var image = this.imageService.getImage();
        final var imgBase64 = Base64.getEncoder().encodeToString(image);
        model.addAttribute("imgContent", imgBase64);

        return "index";
    }

}
