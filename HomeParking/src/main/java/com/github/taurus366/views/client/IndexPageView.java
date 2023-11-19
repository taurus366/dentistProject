package com.github.taurus366.views.client;

import com.github.taurus366.security.AuthenticatedUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.timepicker.TimePickerVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.addons.minicalendar.MiniCalendar;

import java.time.*;
import java.util.ArrayList;
import java.util.List;


@AnonymousAllowed
@PageTitle("Home")
@Route(value = "home")
@RouteAlias(value = "")
public class IndexPageView extends VerticalLayout implements BeforeEnterObserver {
    private final AuthenticatedUser authenticatedUser;
    public IndexPageView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;


        Button buttonBook = new Button("BOOK NOW");
        buttonBook.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Dialog dialogSelectDateTime = new Dialog();
        dialogSelectDateTime.setHeaderTitle("Please select Date and Time");
        VerticalLayout verticalLayout = new VerticalLayout();
        dialogSelectDateTime.add(verticalLayout);


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
        timePicker.getElement().getPropertyNames().forEach(System.out::println);


        dialogSelectDateTime.add(calendar, timePicker);

        buttonBook.addClickListener(event -> {


            dialogSelectDateTime.open();
        });

        add(buttonBook, dialogSelectDateTime);

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

    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            event.forwardTo("user");
        }
    }


}
