package com.music.controller;

import com.music.model.Tag;
import com.music.repository.TagRepository;
import com.music.util.RestPreconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagRepository tagRepository;

    @Autowired
    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTag() {
        return new ResponseEntity<>(tagRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") Long id) {
        Tag tag = RestPreconditions.checkFound(tagRepository.findById(id).orElse(null));
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        tagRepository.save(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") Long id, @RequestBody Tag tag) {
        Tag tagFromDb = RestPreconditions.checkFound(tagRepository.findById(id).orElse(null));
        BeanUtils.copyProperties(tag, tagFromDb, "id");
        tagRepository.save(tagFromDb);
        return new ResponseEntity<>(tagFromDb, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable("id") Long id) {
        Tag tag = RestPreconditions.checkFound(tagRepository.findById(id).orElse(null));
        tagRepository.delete(tag);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
