package com.music.controller;

import com.music.model.Music;
import com.music.repository.MusicRepository;
import com.music.util.RestPreconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {
    private final MusicRepository musicRepository;

    @Autowired
    public MusicController(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @GetMapping
    public ResponseEntity<List<Music>> getAllMusic() {
        return new ResponseEntity<>(musicRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Music> getMusicById(@PathVariable("id") Long id) {
        Music music = RestPreconditions.checkFound(musicRepository.findById(id).orElse(null));
        return new ResponseEntity<>(music, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Music> createMusic(@RequestBody Music music) {
        musicRepository.save(music);
        return new ResponseEntity<>(music, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Music> updateMusic(@PathVariable("id") Long id, @RequestBody Music music) {
        Music musicFromDb = RestPreconditions.checkFound(musicRepository.findById(id).orElse(null));
        BeanUtils.copyProperties(music, musicFromDb, "id");
        musicRepository.save(musicFromDb);
        return new ResponseEntity<>(musicFromDb, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Music> deleteMusic(@PathVariable("id") Long id) {
        Music music = RestPreconditions.checkFound(musicRepository.findById(id).orElse(null));
        musicRepository.delete(music);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

