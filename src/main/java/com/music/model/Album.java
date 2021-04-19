package com.music.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @JsonIgnore
    @OneToMany(mappedBy = "album")
    private List<Music> music;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
}
