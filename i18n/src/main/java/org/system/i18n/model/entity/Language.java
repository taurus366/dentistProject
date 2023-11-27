package org.system.i18n.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "language")
@Data
@AllArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Bulgarian

    private String code; // bg

    private String locale; // bg_BG

    private boolean active; // true


    public Language() {

    }
}
