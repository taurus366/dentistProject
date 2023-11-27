package org.system.i18n.service;

import org.system.i18n.model.dto.LanguageDTO;

import java.util.List;

public interface LanguageService {

    List<LanguageDTO> getAllLanguages();
    List<LanguageDTO> getAllActiveLanguages();
    LanguageDTO getLanguageById(Long langId);
    LanguageDTO getLanguageByLocale(String locale);


}
