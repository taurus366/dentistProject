package org.system.i18n;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.i18n.I18NProvider;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.model.dto.MultiLangualTextAreaDTO;
import org.system.i18n.model.entity.LanguageCustomerEntity;
import org.system.i18n.service.LanguageCustomerService;
import org.system.i18n.service.LanguageService;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;

import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomI18NProvider implements I18NProvider {

    private final LanguageService languageService;
    private final LanguageCustomerService languageCustomerService;

    public static final String BUNDLE_PREFIX = "i18n/translate";

    public final Locale LOCALE_EN = new Locale("en");
    public final Locale LOCALE_BG = new Locale("bg");

    private final List<Locale> locales = List.of(LOCALE_EN, LOCALE_BG);

    private Map<Integer, String> data;

    public CustomI18NProvider(LanguageService languageService, LanguageCustomerService languageCustomerService) {
        this.languageService = languageService;
        this.languageCustomerService = languageCustomerService;
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

    public ComboBox<LanguageDTO> getLanguageSelectorBox(String locale, String title, boolean isFlag) {
        ComboBox<LanguageDTO> languageSelector = new ComboBox<>();

        if(title != null) languageSelector.setLabel(title);

        final Collection<LanguageDTO> allActiveLanguages = this.getAllActiveLanguages();

        languageSelector.setItems(allActiveLanguages);
        languageSelector.setItemLabelGenerator(LanguageDTO::getName);
        if(isFlag)
            languageSelector.setRenderer(new ComponentRenderer<>(language -> {
                CountryFlagT flag = new CountryFlagT(language.getCode(), false);
                flag.getStyle().set("width", "30px").set("height", "29px");

                Span label = new Span(language.getName());

                // Combine the flag and label in a HorizontalLayout
                HorizontalLayout layout = new HorizontalLayout(flag, label);
                layout.setAlignItems(FlexComponent.Alignment.CENTER); // Adjust alignment if needed

                return layout;
            }));

        final LanguageDTO languageByLocale = languageService.getLanguageByLocale(locale);

        languageSelector.setValue(languageByLocale);

        return languageSelector;
    }

    public ComboBox<LanguageDTO> getLanguageSelectorBoxCustomer(String locale, String title, boolean isFlag) {

        ComboBox<LanguageDTO> languageSelector = new ComboBox<>();

        if(title != null) languageSelector.setLabel(title);


        final Collection<LanguageDTO> allActiveLanguages = this.getAllActiveLanguages();

        languageSelector.setItems(allActiveLanguages);
        languageSelector.setItemLabelGenerator(LanguageDTO::getName);
        if(isFlag)
        languageSelector.setRenderer(new ComponentRenderer<>(language -> {
            CountryFlagT flag = new CountryFlagT(language.getCode(), false);
            flag.getStyle().set("width", "30px").set("height", "29px");

            Span label = new Span(language.getName());

            // Combine the flag and label in a HorizontalLayout
            HorizontalLayout layout = new HorizontalLayout(flag, label);
            layout.setAlignItems(FlexComponent.Alignment.CENTER); // Adjust alignment if needed

            return layout;
        }));

        final LanguageDTO languageById = languageService.getLanguageByLocale(locale);

        languageSelector.setValue(languageById);

        return languageSelector;
    }

    public List<LanguageDTO> getAllActiveLanguages() {
        return this.languageService.getAllActiveLanguages();
    }
//    public MultiLangualTextAreaDTO multiLanguageTextArea(Map<Integer, String> data) {
//        CustomI18NProvider provider = new CustomI18NProvider(languageService, languageCustomerService);
//        final List<LanguageDTO> allActiveLanguages = provider.getAllActiveLanguages();
//        AtomicInteger currentLanguageIndex = new AtomicInteger(1);
//        this.data = data;
//
//        HorizontalLayout layout = new HorizontalLayout();
//        layout.setSpacing(false);
//        layout.getStyle()
//                .set("display", "flex")
//                .set("flex-direction", "column")
////                .set("align-items", "center")
//                .set("width", "100%");
//
//        TextArea textArea = new TextArea();
//        textArea.getStyle()
//                .set("width", "100%")
//                .set("max-height", "58px")
//                .set("min-height", "58px");
//
//        Section sectionTextAreaAndFlag = new Section();
//        sectionTextAreaAndFlag.getStyle()
//                .set("display", "flex")
//                .set("width", "100%")
//                .set("align-items", "center")
//                .set("justify-content", "space-around");
//
//
//        CountryFlagT flag = new CountryFlagT(allActiveLanguages.get(currentLanguageIndex.get()).getCode(), false);
//
//        Integer selectedLanguageId1 = Integer.parseInt(String.valueOf(allActiveLanguages.get(currentLanguageIndex.get()).getId()));
//        String dataText1 = this.data.getOrDefault(selectedLanguageId1, ""); // Get the text associated with the selected language ID
//
//        textArea.setValue(dataText1);
//
//        flag.addClickListener(event -> {
//
//            if(currentLanguageIndex.get() + 1 < allActiveLanguages.size()){
//                flag.setCountry(allActiveLanguages.get(currentLanguageIndex.addAndGet(1)).getCode());
//            } else {
//                currentLanguageIndex.set(0);
//                flag.setCountry(allActiveLanguages.get(currentLanguageIndex.get()).getCode());
//            }
//
//            Integer selectedLanguageId = Integer.parseInt(String.valueOf(allActiveLanguages.get(currentLanguageIndex.get()).getId()));
//            String dataText = this.data.getOrDefault(selectedLanguageId, ""); // Get the text associated with the selected language ID
//
//            textArea.setValue(dataText);
//        });
//        textArea.addValueChangeListener(event -> {
//            Integer selectedLanguageId = Integer.parseInt(String.valueOf(allActiveLanguages.get(currentLanguageIndex.get()).getId()));
//            this.data.put(selectedLanguageId, textArea.getValue());
//
//        });
//
//        Button btnSave = new Button("Save");
//
//
//        sectionTextAreaAndFlag.add(textArea, flag);
//
//        layout.add(sectionTextAreaAndFlag, btnSave);
//
//        MultiLangualTextAreaDTO dto = new MultiLangualTextAreaDTO();
//        dto.setTextArea(textArea);
//        dto.setData(this.data);
//        dto.setButton(btnSave);
//        dto.setHorizontalLayout(layout);
//        dto.setFlag(flag);
//
//        return dto;
//    }
}
