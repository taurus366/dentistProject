package org.system.i18n;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.stereotype.Component;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.model.dto.MultiLangualTextAreaDTO;
import org.system.i18n.service.LanguageCustomerService;
import org.system.i18n.service.LanguageService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class MultilLanguageTextArea {

    private final LanguageService languageService;
    private final LanguageCustomerService languageCustomerService;

    private Map<Integer, String> data;

    AtomicInteger currentLanguageIndex;

    TextArea textArea;

    public MultilLanguageTextArea(LanguageService languageService, LanguageCustomerService languageCustomerService) {
        this.languageService = languageService;
        this.languageCustomerService = languageCustomerService;
    }

    public void clearTextArea() {
//       this.data = new HashMap<>();
//       textArea.clear();
//        currentLanguageIndex.set(0);
        reset();
    }

    public void reset() {
        this.data = new HashMap<>();
        textArea.clear();

        // Set the text and flag to the initial state
        AtomicInteger currentLanguageIndex = new AtomicInteger(0);
        CustomI18NProvider provider = new CustomI18NProvider(languageService, languageCustomerService);
        List<LanguageDTO> allActiveLanguages = provider.getAllActiveLanguages();
        CountryFlagT flag = new CountryFlagT(allActiveLanguages.get(currentLanguageIndex.get()).getCode(), false);
        flag.setCountry(allActiveLanguages.get(currentLanguageIndex.get()).getCode());
        Integer selectedLanguageId = Integer.parseInt(String.valueOf(allActiveLanguages.get(currentLanguageIndex.get()).getId()));
        String dataText = this.data.getOrDefault(selectedLanguageId, "");
        textArea.setValue(dataText);

        // Reset the event listeners or any other configurations if needed
    }


    public MultiLangualTextAreaDTO multiLanguageTextArea(Map<Integer, String> data) {
        CustomI18NProvider provider = new CustomI18NProvider(languageService, languageCustomerService);
        final List<LanguageDTO> allActiveLanguages = provider.getAllActiveLanguages();
        currentLanguageIndex = new AtomicInteger(0);
        this.data = data;

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(false);
        layout.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("width", "100%");

        textArea = new TextArea();
        textArea.getStyle()
                .set("width", "100%")
                .set("max-height", "58px")
                .set("min-height", "58px");

        Section sectionTextAreaAndFlag = new Section();
        sectionTextAreaAndFlag.getStyle()
                .set("display", "flex")
                .set("width", "100%")
                .set("align-items", "center")
                .set("justify-content", "space-around");


        CountryFlagT flag = new CountryFlagT(allActiveLanguages.get(currentLanguageIndex.get()).getCode(), false);

        Integer selectedLanguageId1 = Integer.parseInt(String.valueOf(allActiveLanguages.get(currentLanguageIndex.get()).getId()));
        String dataText1 = this.data.getOrDefault(selectedLanguageId1, ""); // Get the text associated with the selected language ID

        textArea.setValue(dataText1);

        flag.addClickListener(event -> {
            if(currentLanguageIndex.get() + 1 < allActiveLanguages.size()){
                flag.setCountry(allActiveLanguages.get(currentLanguageIndex.addAndGet(1)).getCode());
            } else {
                currentLanguageIndex.set(0);
                flag.setCountry(allActiveLanguages.get(currentLanguageIndex.get()).getCode());
            }

            Integer selectedLanguageId = Integer.parseInt(String.valueOf(allActiveLanguages.get(currentLanguageIndex.get()).getId()));
            String dataText = this.data.getOrDefault(selectedLanguageId, ""); // Get the text associated with the selected language ID

            textArea.setValue(dataText);
        });
        textArea.addValueChangeListener(event -> {
            Integer selectedLanguageId = Integer.parseInt(String.valueOf(allActiveLanguages.get(currentLanguageIndex.get()).getId()));
            this.data.put(selectedLanguageId, textArea.getValue());

        });

//        Button btnSave = new Button("Save");


        sectionTextAreaAndFlag.add(textArea, flag);

        layout.add(sectionTextAreaAndFlag);

        MultiLangualTextAreaDTO dto = new MultiLangualTextAreaDTO();
        dto.setTextArea(textArea);
        dto.setData(this.data);
        dto.setHorizontalLayout(layout);
        dto.setFlag(flag);

        return dto;
    }}
