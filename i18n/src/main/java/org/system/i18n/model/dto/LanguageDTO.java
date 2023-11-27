package org.system.i18n.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.system.i18n.model.entity.Language;

@Data
@AllArgsConstructor
public class LanguageDTO {

    private Long id;

    private String name; // Bulgarian

    private String code; // bg

    private String locale; // bg_BG

    private boolean isActive; // true

    public LanguageDTO(Language language) {
        this.id = language.getId();
        this.name = language.getName();
        this.isActive = language.isActive();
        this.locale = language.getLocale();
        this.code = language.getCode();
    }

    public LanguageDTO() {
    }
}
