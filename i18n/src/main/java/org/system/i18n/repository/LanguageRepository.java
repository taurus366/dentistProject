package org.system.i18n.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.system.i18n.model.entity.LanguageEntity;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {

    List<LanguageEntity> findAllByActive(boolean active);
    LanguageEntity findByLocale(String locale);

    LanguageEntity findByDefaultLang(Boolean aBoolean);

}
