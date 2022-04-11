package com.springboot.videoservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long ordinalNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Channel channel;

    //@ManyToMany
    //private List<Video> videos;

    //@ManyToMany
    //private List<Category> categories;
}
