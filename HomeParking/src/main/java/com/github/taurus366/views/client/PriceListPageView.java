package com.github.taurus366.views.client;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.stefan.table.Table;
import org.vaadin.stefan.table.TableRow;

import java.util.concurrent.atomic.AtomicInteger;

@AnonymousAllowed
@PageTitle("Price list")
@Route(value = "price_list")
public class PriceListPageView extends VerticalLayout {


    public PriceListPageView() {

        Image imageIcon = new Image("images/logo.png", "logo");
        imageIcon.getStyle()
                .set("filter", "brightness(999) invert(0.5) sepia(1) hue-rotate(111deg)")
                .set("width", "205px")
                .set("height", "122px");

        Button buttonMain = new Button("Main");
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

        TableRow headerRow1 = table.addRow();

        headerRow1.addHeaderCell().setText("COSMETICS");
        headerRow1.getCell(0).ifPresent(tableCell -> {
            tableCell.getStyle()
                    .set("position", "relative")
                    .set("left", "50%")
                    .set("transform", "translate(-50%, 0%)");
        });


        TableRow headerRow = table.addRow();
        headerRow.getStyle().set("background", "chartreuse");
        headerRow.addHeaderCell().setText("NAME");
        headerRow.addHeaderCell().setText("CURRENCY");
        headerRow.addHeaderCell().setText("PRICE");

        TableRow detailsRow = table.addRow();
        detailsRow.addDataCell().setText("TEST1");
        detailsRow.addDataCell().setText("BGN");
        detailsRow.addDataCell().setText("2.22");

        TableRow detailsRow2 = table.addRow();
        detailsRow2.addDataCell().setText("TEST1");
        detailsRow2.addDataCell().setText("BGN");
        detailsRow2.addDataCell().setText("2.22");

        TableRow detailsRow3 = table.addRow();
        detailsRow3.addDataCell().setText("TEST1");
        detailsRow3.addDataCell().setText("BGN");
        detailsRow3.addDataCell().setText("2.22");


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
