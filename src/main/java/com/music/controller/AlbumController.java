package com.music.controller;

import com.music.model.Album;
import com.music.repository.AlbumRepository;
import com.music.util.RestPreconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumController(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbum() {
        return new ResponseEntity<>(albumRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable("id") Long id) {
        Album album = RestPreconditions.checkFound(albumRepository.findById(id).orElse(null));
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
        albumRepository.save(album);
        return new ResponseEntity<>(album, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable("id") Long id, @RequestBody Album album) {
        Album albumFromDb = RestPreconditions.checkFound(albumRepository.findById(id).orElse(null));
        BeanUtils.copyProperties(album, albumFromDb, "id");
        albumRepository.save(albumFromDb);
        return new ResponseEntity<>(albumFromDb, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Album> deleteAlbum(@PathVariable("id") Long id) {
        Album album = RestPreconditions.checkFound(albumRepository.findById(id).orElse(null));
        albumRepository.delete(album);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
