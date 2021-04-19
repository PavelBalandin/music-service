package com.music.controller;

import com.music.model.Image;
import com.music.repository.ImageRepository;
import com.music.util.RestPreconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImage() {
        return new ResponseEntity<>(imageRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable("id") Long id) {
        Image image = RestPreconditions.checkFound(imageRepository.findById(id).orElse(null));
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        imageRepository.save(image);
        return new ResponseEntity<>(image, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable("id") Long id, @RequestBody Image image) {
        Image imageFromDb = RestPreconditions.checkFound(imageRepository.findById(id).orElse(null));
        BeanUtils.copyProperties(image, imageFromDb, "id");
        imageRepository.save(imageFromDb);
        return new ResponseEntity<>(imageFromDb, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Image> deleteImage(@PathVariable("id") Long id) {
        Image image = RestPreconditions.checkFound(imageRepository.findById(id).orElse(null));
        imageRepository.delete(image);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
