package org.system.i18n.service;

import org.system.i18n.model.entity.LanguageCustomerEntity;

import java.util.List;
import java.util.Optional;

public interface LanguageCustomerService {

        LanguageCustomerEntity findBySession(String session);
        void save(LanguageCustomerEntity en1);
}
