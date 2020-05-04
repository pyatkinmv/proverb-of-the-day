package ru.pyatkinmv.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class Proverb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024, nullable = false)
    private String text;

    @Column(length = 2048, nullable = false)
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private boolean isSupplied;

    public Proverb(String text, String description) {
        this.text = text;
        this.description = description;
    }
}

