package ru.pyatkinmv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Proverb {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1024, nullable = false)
    private String text;

    @Column(length = 2048, nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isSupplied = false;
}
