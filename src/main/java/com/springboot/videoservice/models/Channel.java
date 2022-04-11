package com.springboot.videoservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="channel")
@Data
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToOne(mappedBy = "channel")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "channel",
            orphanRemoval = true)
    private List<Playlist> playlists;
}
