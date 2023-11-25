package com.github.taurus366.views.client;

import com.github.taurus366.security.AuthenticatedUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.timepicker.TimePickerVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.addons.minicalendar.MiniCalendar;

import java.time.*;


@AnonymousAllowed
@PageTitle("Home")
@Route(value = "home")
@RouteAlias(value = "")
public class IndexPageView extends FlexLayout implements BeforeEnterObserver {
    private final AuthenticatedUser authenticatedUser;
    public IndexPageView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;


        Button buttonBook = new Button("РЕЗЕРВИРАЙТЕ СЕГА");
        buttonBook.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        /// DIALOG FOR SELECT DATE AND TIME
        Dialog dialogSelectDateTime = new Dialog();

        VerticalLayout verticalLayout1 = new VerticalLayout();
        H3 titleDialog = new H3("Моля, изберете дата и час");
        titleDialog.getStyle().set("margin", "auto");
        verticalLayout1.add(titleDialog);
        dialogSelectDateTime.getHeader().add(verticalLayout1);
        VerticalLayout verticalLayout = new VerticalLayout();
        dialogSelectDateTime.add(verticalLayout);
        dialogSelectDateTime.setHeight("465px");


        Button cancelBtn = new Button("cancel");
        Button nextBtn = new Button("next");

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
            timePicker.setEnabled(true);


        });

        dialogSelectDateTime.add(calendar, timePicker);

        buttonBook.addClickListener(event -> {


            dialogSelectDateTime.open();
        });

//        add(buttonBook, dialogSelectDateTime);

//        Button button = new Button("CLICK ME TO REDIREC TO LOGIN");
//
//        button.addClickListener(event -> {
//
//           getUI().ifPresent(ui -> {
//
//               ui.navigate("login");
//           });
//        });
//
//        add(button);



        /// DESIGN FOR PAGE

        // HEADER

        Button btnLogin = new Button("Влез");
        btnLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnLogin.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("login")));

        Button btnBook2 = new Button("Резервирай час");
        btnBook2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnBook2.addClickListener(event -> dialogSelectDateTime.open());
        btnBook2.getStyle().set("margin-left", "10px");

        Image imageIcon = new Image("images/logo.png", "logo");
        imageIcon.getStyle()
                .set("filter", "brightness(999) invert(0.5) sepia(1) hue-rotate(111deg)")
                .set("width", "205px")
                .set("height", "122px");

        UnorderedList ul = new UnorderedList();
//        ul.getStyle().set("margin", "5px");
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


           Paragraph paragraph = new Paragraph("Зъболекар с висок професионализъм и отношение към пациентите.");
           paragraph.getStyle()
                   .set("font-size", "45px")
                   .set("line-height", "normal")
                   .set("color", "#BB8150")
                   .set("font-weight", "bold")
                   .set("font-family", "Overpass");
           Paragraph paragraph1 = new Paragraph("Изберете най-добрите специалисти с висок професионализъм и топло отношение, където никога няма място за осъждане.");
           paragraph1.getStyle()
                   .set("font-size", "20px")
                   .set("font-family", "Overpass");


           Article articleText = new Article();
           articleText.setWidth("70%");
           articleText.getStyle().set("padding-right", "30px");
        articleText.add(paragraph, paragraph1, buttonBook);
        articleText.getStyle().set("align-self", "self-end");

           Article articleImg = new Article();
//        articleImg.setWidth("100%");
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


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            event.forwardTo("user");
        }
    }


}
