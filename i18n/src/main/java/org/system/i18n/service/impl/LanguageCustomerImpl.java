package org.system.i18n.service.impl;

import org.springframework.stereotype.Service;
import org.system.i18n.model.entity.LanguageCustomerEntity;
import org.system.i18n.repository.LanguageCustomerRepository;
import org.system.i18n.service.LanguageCustomerService;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageCustomerImpl implements LanguageCustomerService {

    private final LanguageCustomerRepository languageCustomerRepository;

    public LanguageCustomerImpl(LanguageCustomerRepository languageCustomerRepository) {
        this.languageCustomerRepository = languageCustomerRepository;
    }

    @Override
    public LanguageCustomerEntity findBySession(String session) {
        return languageCustomerRepository.findBySession(session);
    }

    @Override
    public void save(LanguageCustomerEntity en1) {
        languageCustomerRepository.save(en1);
    }
}
