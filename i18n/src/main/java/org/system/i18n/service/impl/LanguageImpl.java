package org.system.i18n.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.model.entity.Language;
import org.system.i18n.repository.LanguageRepository;
import org.system.i18n.service.LanguageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    private final ModelMapper modelMapper;

    public LanguageImpl(LanguageRepository languageRepository, ModelMapper modelMapper) {
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LanguageDTO> getAllLanguages() {
                    return this.languageRepository.findAll()
                            .stream()
                            .map(LanguageDTO::new)
                            .collect(Collectors.toList());
    }

    @Override
    public List<LanguageDTO> getAllActiveLanguages() {
        return this.languageRepository.findAllByActive(true)
                .stream()
                .map(LanguageDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public LanguageDTO getLanguageById(Long langId) {
        return this.languageRepository.findById(langId)
                .map(LanguageDTO::new).orElse(null);
    }

    @Override
    public LanguageDTO getLanguageByLocale(String locale) {
        final Language byLocale = this.languageRepository.findByLocale(locale);
        return modelMapper.map(byLocale, LanguageDTO.class);
    }
}
