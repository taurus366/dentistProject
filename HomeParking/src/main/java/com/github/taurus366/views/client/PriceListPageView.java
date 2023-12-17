package com.github.taurus366.views.client;

import com.github.taurus366.model.entity.PriceListEntity;
import com.github.taurus366.model.service.PriceListService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.system.i18n.CustomI18NProvider;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.model.entity.LanguageCustomerEntity;
import org.system.i18n.service.LanguageCustomerService;
import org.system.i18n.service.LanguageService;
import org.vaadin.stefan.table.Table;
import org.vaadin.stefan.table.TableRow;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@AnonymousAllowed
@StyleSheet("./themes/homeparking/price_list_style.css")
@PageTitle("Price list")
@Route(value = "price_list")
public class PriceListPageView extends VerticalLayout implements BeforeEnterObserver {

    private final LanguageCustomerService languageCustomerService;
    private final LanguageService languageService;

    private final Button buttonMain;
    private static String NAME = "NAME";
    private static String CURRENCY = "CURRENCY";
    private static String PRICE = "PRICE";
    private final Span nameSpan;
    private final Span currencySpan;
    private final Span priceSpan;


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String userSession = event.getUI().getSession().getSession().getId();

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

        buttonMain.setText(languageProvider.getTranslation("Main", Locale.of(userLocale)));
        currencySpan.setText(languageProvider.getTranslation("Currency", Locale.of(userLocale)).toUpperCase());
        priceSpan.setText(languageProvider.getTranslation("Price", Locale.of(userLocale)).toUpperCase());
        nameSpan.setText(languageProvider.getTranslation("Name", Locale.of(userLocale)).toUpperCase());
    }

    public PriceListPageView(PriceListService priceListService, LanguageCustomerService languageCustomerService, LanguageService languageService) {
        this.languageCustomerService = languageCustomerService;
        this.languageService = languageService;
        Image imageIcon = new Image("images/logo.png", "logo");
        imageIcon.getStyle()
                .set("filter", "brightness(999) invert(0.5) sepia(1) hue-rotate(111deg)")
                .set("width", "205px")
                .set("height", "122px");

        buttonMain = new Button("Main");
        buttonMain.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonMain.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("")));

        UnorderedList ul = new UnorderedList();
        ul.add(buttonMain);



        Section sectionHeader = new Section();
        sectionHeader.addClassName("header-sectionMain");
        sectionHeader.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("width", "96%")
                .set("justify-content", "space-between");
        sectionHeader.add(imageIcon, ul);




        Div priceDiv = new Div();
        priceDiv.getStyle()
                .set("max_width", "1440px")
                .set("margin", "auto")
                .set("max-width", "1440px")
                .set("width", "100%");
        priceDiv.setClassName("price-container");

        Table table = new Table();
        table.getStyle()
                .set("width", "470px")
//                .set("border", "1px solid black")
                .set("border-collapse", "collapse")
                .set("text-align", "center")
                .set("margin", "auto");

        table.getElement().setAttribute("data-hover", "hover_effect");

        table.addClassName("price-table");

        TableRow headerRow1 = table.addRow();

        headerRow1.addHeaderCell().setText("COSMETICS");
        headerRow1.getCell(0).ifPresent(tableCell -> {
            tableCell.getStyle()
                    .set("position", "relative")
                    .set("left", "50%")
                    .set("transform", "translate(-50%, 0%)");
        });

        nameSpan = new Span("NAME");
        priceSpan = new Span("PRICE");
        currencySpan = new Span("CURRENCY");

        TableRow headerRow = table.addRow();
        headerRow.getStyle().set("background", "chartreuse");
        headerRow.addHeaderCell().add(nameSpan);
        headerRow.addHeaderCell().add(priceSpan);
        headerRow.addHeaderCell().add(currencySpan);

        for (PriceListEntity priceListEntity : priceListService.findAll()) {
            TableRow detailsRow = table.addRow();
            detailsRow.addDataCell().setText(priceListEntity.getName().get(1));
            detailsRow.addDataCell().setText(String.valueOf(priceListEntity.getPrice()));
            detailsRow.addDataCell().setText(priceListEntity.getCurrency().getTitle());
        }

//        TableRow detailsRow2 = table.addRow();
//        detailsRow2.addDataCell().setText("TEST1");
//        detailsRow2.addDataCell().setText("BGN");
//        detailsRow2.addDataCell().setText("2.22");


        AtomicInteger counter = new AtomicInteger();
            table.getChildren().forEach(component -> {
              if(counter.get() >= 1 && counter.get() % 2 == 0){
                  component.getStyle().set("background", "#F8F6F2");
              }
              counter.addAndGet(1);
            });


        priceDiv.add(sectionHeader, table);
        add(priceDiv);
        }


}
