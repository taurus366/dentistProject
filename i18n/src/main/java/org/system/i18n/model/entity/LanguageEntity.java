package org.system.i18n.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "language")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageEntity extends BaseEntity{

    private String name; // Bulgarian

    private String code; // bg

    private String locale; // bg_BG

    private boolean active; // true

    private boolean defaultLang = false;
}
