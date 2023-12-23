package org.system.i18n.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.system.i18n.model.entity.LanguageEntity;

@Data
@AllArgsConstructor
public class LanguageDTO {

    private Long id;

    private String name; // Bulgarian

    private String code; // bg

    private String locale; // bg_BG

    private boolean isActive; // true

    public LanguageDTO(LanguageEntity languageEntity) {
        this.id = languageEntity.getId();
        this.name = languageEntity.getName();
        this.isActive = languageEntity.isActive();
        this.locale = languageEntity.getLocale();
        this.code = languageEntity.getCode();
    }

    public LanguageDTO() {
    }
}
