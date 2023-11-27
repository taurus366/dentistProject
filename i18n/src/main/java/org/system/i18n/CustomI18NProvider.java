package org.system.i18n;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.i18n.I18NProvider;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.service.LanguageService;

import java.text.MessageFormat;
import java.util.*;

@Component
public class CustomI18NProvider implements I18NProvider {

    private final LanguageService languageService;

    public static final String BUNDLE_PREFIX = "i18n/translate";

    public final Locale LOCALE_EN = new Locale("en");
    public final Locale LOCALE_BG = new Locale("bg");

    private final List<Locale> locales = List.of(LOCALE_EN, LOCALE_BG);

    public CustomI18NProvider(LanguageService languageService) {
        this.languageService = languageService;
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {

        if( key == null) {
            LoggerFactory.getLogger(CustomI18NProvider.class.getName())
                    .warn("Got language request for key with null value!");
            return "";
        }

//        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);
        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, Locale.of(locale.toString().split("_")[0]));


        String value;
        try {
            value = bundle.getString(key);
        } catch (final MissingResourceException e) {
            LoggerFactory.getLogger(CustomI18NProvider.class.getName())
                    .warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;

    }

    public ComboBox<LanguageDTO> getLanguageSelectorBox(String locale, String title) {
        ComboBox<LanguageDTO> languageSelector = new ComboBox<>(title);

        final Collection<LanguageDTO> allActiveLanguages = this.getAllActiveLanguages();

        languageSelector.setItems(allActiveLanguages);
        languageSelector.setItemLabelGenerator(LanguageDTO::getName);
//        languageSelector.setItems(LOCALE_BG, LOCALE_EN);
//            languageSelector.setItems(allActiveLanguages
//                    .stream()
//                    .map(LanguageDTO::getName)
//                    .collect(Collectors.toList()));
//                languageSelector.setItems(Map.of("a", "1"));


        final LanguageDTO languageByLocale = languageService.getLanguageByLocale(locale);

        languageSelector.setValue(languageByLocale);

//        languageSelector.setValue(VaadinService.getCurrentRequest().getLocale());

//        languageSelector.addValueChangeListener(event -> {
//            System.out.println(event);
////            UI.getCurrent().setDirection(Direction.LEFT_TO_RIGHT);
//////            Locale selectedLocale = event.getValue();
////            UI.getCurrent().setLocale(LOCALE_BG);
//
//            UI.getCurrent().getPage().reload();
//        });
//        https://github.com/vaadin/vaadin-localization-example
        return languageSelector;
    }

    public List<LanguageDTO> getAllLanguages() {
        return this.languageService.getAllLanguages();
    }

    public List<LanguageDTO> getAllActiveLanguages() {
        return this.languageService.getAllActiveLanguages();
    }

    public LanguageDTO getLanguageById(Long langId) {
        return this.languageService.getLanguageById(langId);
    }

    public LanguageDTO getLanguageByLocale(String locale) {
        return this.languageService.getLanguageByLocale(locale);
    }
}
