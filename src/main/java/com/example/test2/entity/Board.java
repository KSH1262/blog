package com.example.test2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.jsoup.Jsoup;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @ColumnDefault("0")
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> reply;

    @CreationTimestamp
    private LocalDateTime createDate;

    private String previewText;

    @Transient
    public String getContentPreview() {
        String cleaned = content.replaceAll("<img[^>]*>", "");
        String text = Jsoup.parse(cleaned).text();
        return text.length() > 25 ? text.substring(0, 25) + "..." : text;
    }


}
