package com.music.controller;

import com.music.model.Artist;
import com.music.repository.ArtistRepository;
import com.music.util.RestPreconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtist() {
        return new ResponseEntity<>(artistRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable("id") Long id) {
        Artist artist = RestPreconditions.checkFound(artistRepository.findById(id).orElse(null));
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        artistRepository.save(artist);
        return new ResponseEntity<>(artist, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable("id") Long id, @RequestBody Artist artist) {
        Artist artistFromDb = RestPreconditions.checkFound(artistRepository.findById(id).orElse(null));
        BeanUtils.copyProperties(artist, artistFromDb, "id");
        artistRepository.save(artistFromDb);
        return new ResponseEntity<>(artistFromDb, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable("id") Long id) {
        Artist artist = RestPreconditions.checkFound(artistRepository.findById(id).orElse(null));
        artistRepository.delete(artist);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
