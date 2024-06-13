package com.dragan.emuson.api.yongniverse.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "yongniverse")
@Getter
public class Yongniverse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_boss")
    private boolean isBoss;

    private int likes;

    private int dislikes;

    @Column(nullable = false)
    private String speciality;

    private String snsUrl;

    private String thumbnailUrl;

    private String logoUrl;

    private Boolean isDragonTeam;

    // memo 240210: https://chat.openai.com/share/28a041ac-1627-400e-a6d1-526aa456e391
    @Version
    private int version;

    public void addLikes() {
        this.likes += 1;
    }

    public void addDislikes() {
        this.dislikes += 1;
    }


}
