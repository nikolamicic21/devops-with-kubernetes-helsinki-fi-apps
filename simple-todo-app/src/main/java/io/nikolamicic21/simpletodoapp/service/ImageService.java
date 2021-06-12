package io.nikolamicic21.simpletodoapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class ImageService {

    private final Path imageCacheFilePath;
    private final String imageFilenamePattern;
    private final WebClient webClient;

    public ImageService(
            @Value("${image.cache.file-path}") String imageCacheFilePath,
            @Value("${image.cache.filename.pattern}") String imageFilenamePattern,
            @Value("${image.service.url}") String imageServiceUrl,
            WebClient.Builder webClientBuilder) {
        this.imageCacheFilePath = Paths.get(imageCacheFilePath);
        this.imageFilenamePattern = imageFilenamePattern;
        this.webClient = webClientBuilder
                .baseUrl(imageServiceUrl)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)))
                .build();
    }

    @Nullable
    public byte[] getImage() {
        return tryGetFromImageCache()
                .orElseGet(this::getFromImageService);
    }

    @Nullable
    private byte[] getFromImageService() {
        final var image = this.webClient.get()
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(byte[].class))
                .block();
        if (image != null) {
            saveImageToCache(image);
        }

        return image;
    }

    private void saveImageToCache(@NonNull byte[] image) {
        try {
            if (!Files.exists(this.imageCacheFilePath)) {
                Files.createDirectory(this.imageCacheFilePath);
            }
            Files.walk(this.imageCacheFilePath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
            final var imageFilename = MessageFormat
                    .format(this.imageFilenamePattern, LocalDate.now());
            final var imageFilePath = this.imageCacheFilePath
                    .resolve(imageFilename);
            Files.write(imageFilePath, image);
        } catch (IOException e) {
            log.warn("Error saving image to cache. ", e);
        }
    }

    @NonNull
    private Optional<byte[]> tryGetFromImageCache() {
        byte[] image = null;
        try {
            final var imageFilename = MessageFormat
                    .format(this.imageFilenamePattern, LocalDate.now());
            final var imageFilePath = this.imageCacheFilePath
                    .resolve(imageFilename);
            if (Files.exists(imageFilePath)) {
                image = Files.readAllBytes(imageFilePath);
            }
        } catch (IOException e) {
            log.trace("Error getting image from cache", e);
        }

        return image == null
                ? Optional.empty()
                : Optional.of(image);
    }

}
