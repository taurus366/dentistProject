package com.github.taurus366.views.client;

import com.github.taurus366.security.AuthenticatedUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.timepicker.TimePickerVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.system.i18n.CustomI18NProvider;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.model.entity.LanguageCustomerEntity;
import org.system.i18n.service.LanguageCustomerService;
import org.system.i18n.service.LanguageService;
import org.vaadin.addons.minicalendar.MiniCalendar;

import java.time.*;
import java.util.Locale;


@AnonymousAllowed
@StyleSheet("./themes/homeparking/index_page_view.css")
@PageTitle("Home")
@Route(value = "home")
@RouteAlias(value = "")
public class IndexPageView extends FlexLayout implements BeforeEnterObserver {
    private final AuthenticatedUser authenticatedUser;

    private final LanguageCustomerService languageCustomerService;
    private final LanguageService languageService;

    private final Button buttonBook;
    private final Button nextBtn;
    private final H3 titleDialog;
    private final Button cancelBtn;
    private final Button btnLogin;
    private final Button btnBook2;
    private final Button priceListBtn;
    private final UnorderedList ul;
    private final Paragraph paragraph;
    private final Paragraph paragraph1;
    private final Paragraph paragraphCall;
    private final  Span span;

    @Override
    public void beforeEnter(BeforeEnterEvent event1) {
        if (authenticatedUser.get().isPresent()) {
            event1.forwardTo("user");
        }
        String userSession = event1.getUI().getSession().getSession().getId();


        // 1 check if the session is exists on db
        LanguageCustomerEntity bySession = languageCustomerService.findBySession(userSession);
        // 2 if doesnt exists on db , create new record then set language as a BG by deffault
        if(bySession == null) {
            LanguageDTO deffaultLang = languageService.getLanguageByDeffault();
            LanguageCustomerEntity newEntity = new LanguageCustomerEntity();
            newEntity.setSession(userSession);
            newEntity.setLocale(deffaultLang.getLocale());
            languageCustomerService.save(newEntity);
            bySession = newEntity;
        }
        String userLocale = bySession.getLocale();

        CustomI18NProvider languageProvider = new CustomI18NProvider(languageService, languageCustomerService);
        final ComboBox<LanguageDTO> getLanguageSelectorBoxCustomer = languageProvider.getLanguageSelectorBoxCustomer(userLocale, null, true);
        LanguageCustomerEntity finalBySession = bySession;
        getLanguageSelectorBoxCustomer.addValueChangeListener(event -> {
            finalBySession.setLocale(event.getValue().getLocale());
            languageCustomerService.save(finalBySession);
            UI.getCurrent().getPage().reload();
        });
        getLanguageSelectorBoxCustomer.getStyle().set("margin-right", "10px");

        ul.addComponentAsFirst(getLanguageSelectorBoxCustomer);

        buttonBook.setText(languageProvider.getTranslation("BookNow", Locale.of(userLocale)));
        nextBtn.setText(languageProvider.getTranslation("Next", Locale.of(userLocale)));
        titleDialog.setText(languageProvider.getTranslation("SelectDateTimePlease", Locale.of(userLocale)));
        cancelBtn.setText(languageProvider.getTranslation("Cancel", Locale.of(userLocale)));
        btnLogin.setText(languageProvider.getTranslation("Login", Locale.of(userLocale)));
        btnBook2.setText(languageProvider.getTranslation("BookNow", Locale.of(userLocale)));
        priceListBtn.setText(languageProvider.getTranslation("Prices", Locale.of(userLocale)));
        paragraph.setText(languageProvider.getTranslation("DentistText1", Locale.of(userLocale)));
        paragraph1.setText(languageProvider.getTranslation("DentistText2", Locale.of(userLocale)));
        paragraphCall.setText(languageProvider.getTranslation("DentistText3", Locale.of(userLocale)));
        paragraphCall.add(span);
    }

    public IndexPageView(AuthenticatedUser authenticatedUser, LanguageService languageService, LanguageCustomerService languageCustomerService1) {
        this.authenticatedUser = authenticatedUser;
        this.languageCustomerService = languageCustomerService1;
        this.languageService = languageService;

        buttonBook = new Button("Book now");
        buttonBook.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        /// DIALOG FOR SELECT DATE AND TIME
        Dialog dialogSelectDateTime = new Dialog();

        VerticalLayout verticalLayout1 = new VerticalLayout();
        titleDialog = new H3("Моля, изберете дата и час");
        titleDialog.getStyle().set("margin", "auto");
        verticalLayout1.add(titleDialog);
        dialogSelectDateTime.getHeader().add(verticalLayout1);
        VerticalLayout verticalLayout = new VerticalLayout();
        dialogSelectDateTime.add(verticalLayout);
        dialogSelectDateTime.setHeight("465px");


        cancelBtn = new Button("cancel");
//        Button nextBtn = new Button("next");
        nextBtn = new Button();

        cancelBtn.addClickListener(event -> dialogSelectDateTime.close());

        dialogSelectDateTime.getFooter().add(cancelBtn);
        dialogSelectDateTime.getFooter().add(nextBtn);



        TimePicker timePicker = new TimePicker();
        timePicker.setStep(Duration.ofMinutes(30));
        timePicker.setMin(LocalTime.of(7, 0));
        timePicker.setMax(LocalTime.of(19, 0));
        timePicker.addThemeVariants(TimePickerVariant.LUMO_ALIGN_LEFT);
//       timePicker.setEnabled(false);
        timePicker.setAutoOpen(true);



        MiniCalendar calendar = new MiniCalendar();
        calendar.setDayEnabledProvider(localDate -> localDate.getDayOfWeek() != DayOfWeek.SATURDAY && localDate.getDayOfWeek() != DayOfWeek.SUNDAY);



        // every selected day should be updated the available times
        calendar.addValueChangeListener(event -> {
            Notification.show("Value changed to " + event.getValue());
                //1. must check db for free hours
                //2. must show free hours
//            timePicker.setEnabled(true);


        });

        dialogSelectDateTime.add(calendar, timePicker);

        buttonBook.addClickListener(event -> {


            dialogSelectDateTime.open();
        });

        /// DESIGN FOR PAGE

        // HEADER

        btnLogin = new Button("Влез");
        btnLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnLogin.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("login")));

        btnBook2 = new Button("Резервирай час");
        btnBook2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnBook2.addClickListener(event -> dialogSelectDateTime.open());
        btnBook2.getStyle().set("margin-left", "10px");

        Image imageIcon = new Image("images/logo.png", "logo");
        imageIcon.getStyle()
                .set("filter", "brightness(999) invert(0.5) sepia(1) hue-rotate(111deg)")
                .set("width", "205px")
                .set("height", "122px");


        priceListBtn = new Button("Цени");
        priceListBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        priceListBtn.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("price_list")));
        priceListBtn.getStyle().set("margin-right", "10px");

        ul = new UnorderedList();
//        ul.add(getLanguageSelectorBoxCustomer);
        ul.add(priceListBtn);
        ul.add(btnLogin);
        ul.add(btnBook2);



        Section sectionHeader = new Section();
        sectionHeader.addClassName("header-sectionMain");
        sectionHeader.getStyle()
                        .set("display", "flex")
                        .set("align-items", "center")
                        .set("width", "96%")
                        .set("justify-content", "space-between");
        sectionHeader.add(imageIcon, ul);



        // MAIN
        Image image = new Image("images/female-dentist.png", "female dentist");
            image.setWidth("100%");
            image.getStyle().set("height", "150%")
                    .set("object-fit", "cover")
                    .set("box-shadow", "11px 7px 20px 0px #8d8d8d");


           paragraph = new Paragraph("Зъболекар с висок професионализъм и отношение към пациентите.");
           paragraph.getStyle()
                   .set("font-size", "45px")
                   .set("line-height", "normal")
                   .set("color", "#BB8150")
                   .set("font-weight", "bold")
                   .set("font-family", "Overpass");
           paragraph1 = new Paragraph("Изберете най-добрите специалисти с висок професионализъм и топло отношение, където никога няма място за осъждане.");
           paragraph1.getStyle()
                   .set("font-size", "20px")
                   .set("font-family", "Overpass");

           paragraphCall = new Paragraph("Или се обадете:");
           paragraphCall.getStyle().set("font-size", "22px");
           span = new Span("0894396766");
           span.getStyle().set("font-weight", "600").set("color", "#BB8150").set("font-family", "Overpass");
           paragraphCall.add(span);


           Article articleText = new Article();
           articleText.setWidth("70%");
           articleText.getStyle().set("padding-right", "30px");
        articleText.add(paragraph, paragraph1, buttonBook, paragraphCall);
        articleText.getStyle().set("align-self", "self-end");
        articleText.addClassName("text-article");

           Article articleImg = new Article();
           articleImg.addClassName("img-article");

           articleImg.add(image);

           Section sectionMain = new Section();
           sectionMain.addClassName("main-sectionMain");
           sectionMain.getStyle()
                   .set("display", "flex")
                   .set("margin", "0 30px 0 30px")
                   .set("flex-wrap", "nowrap");
//                   .set("align-items", "center");
           sectionMain.add(articleText, articleImg);

        Div div = new Div(sectionHeader, sectionMain);
                    div.getStyle()
                        .set("max-width", "1440px")
                        .set("margin", "auto");
        div.setWidth("100%");
        div.setClassName("index-container");




        add(div);






    }





}
