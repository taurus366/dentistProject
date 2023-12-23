package com.github.taurus366.views.profile;

import com.github.taurus366.model.entity.UserEntity;
import com.github.taurus366.model.service.impl.UserService;
import com.github.taurus366.security.AuthenticatedUser;
import com.github.taurus366.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.system.i18n.CustomI18NProvider;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.service.LanguageCustomerService;
import org.system.i18n.service.LanguageService;

import java.util.Locale;
import java.util.Optional;

@PageTitle("Profile")
@Route(value = "profile", layout = MainLayout.class)
@PermitAll
public class ProfileView extends VerticalLayout {

    private String userLocale;


    public ProfileView(LanguageService languageService, AuthenticatedUser authenticatedUser, UserService userService, LanguageCustomerService languageCustomerService) {
        Optional<UserEntity> optionalUser = authenticatedUser.get();
        optionalUser.ifPresent(userEntity -> userLocale = userEntity.getLocale());


        CustomI18NProvider languageProvider = new CustomI18NProvider(languageService, languageCustomerService);
        final ComboBox<LanguageDTO> languageSelectorBox = languageProvider.getLanguageSelectorBox(userLocale, languageProvider.getTranslation("SelectLanguage", Locale.of(userLocale), ""), true);
        languageSelectorBox.addValueChangeListener(event -> {
            optionalUser.ifPresent(userEntity -> {
                userEntity.setLocale(event.getValue().getLocale());
                userService.update(userEntity);
                UI.getCurrent().getPage().reload();
            });
        });
        add(languageSelectorBox);

    }
}
