package io.nikolamicic21.simpletodoapp.controller;

import io.nikolamicic21.simpletodoapp.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/random")
    public String getRandomImage() {
        final var image = this.imageService.getImage();

        return Base64.getEncoder().encodeToString(image);
    }

}
