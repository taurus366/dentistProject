package org.system.i18n.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.system.i18n.model.entity.Language;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findAllByActive(boolean active);
    Language findByLocale(String locale);

}
