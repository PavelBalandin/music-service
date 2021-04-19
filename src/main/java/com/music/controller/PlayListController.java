package com.music.controller;

import com.music.model.PlayList;
import com.music.repository.PlayListRepository;
import com.music.util.RestPreconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlayListController {
    private final PlayListRepository playListRepository;

    @Autowired
    public PlayListController(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    @GetMapping
    public ResponseEntity<List<PlayList>> getAllPlayList() {
        return new ResponseEntity<>(playListRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayList> getPlayListById(@PathVariable("id") Long id) {
        PlayList playList = RestPreconditions.checkFound(playListRepository.findById(id).orElse(null));
        return new ResponseEntity<>(playList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlayList> createPlayList(@RequestBody PlayList playList) {
        playListRepository.save(playList);
        return new ResponseEntity<>(playList, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayList> updatePlayList(@PathVariable("id") Long id, @RequestBody PlayList playList) {
        PlayList playListFromDb = RestPreconditions.checkFound(playListRepository.findById(id).orElse(null));
        BeanUtils.copyProperties(playList, playListFromDb, "id");
        playListRepository.save(playListFromDb);
        return new ResponseEntity<>(playListFromDb, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlayList> deletePlayList(@PathVariable("id") Long id) {
        PlayList playList = RestPreconditions.checkFound(playListRepository.findById(id).orElse(null));
        playListRepository.delete(playList);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

