package com.github.taurus366.views.clientViews;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Route(value = "test")
public class IndexPageView extends VerticalLayout  {

    public IndexPageView() {

        TextField patientName = new TextField("Patient Name");
        DatePicker appointmentDate = new DatePicker("Appointment Date");
        Button scheduleButton = new Button("Schedule Appointment");

        // Set up form layout
        FormLayout formLayout = new FormLayout();
        formLayout.add(patientName, appointmentDate, scheduleButton);

        // Add a click listener to the button
        scheduleButton.addClickListener(event -> {
            // Perform scheduling logic here (e.g., save to database)
            Notification.show("Appointment scheduled for " + appointmentDate.getValue());

            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        // Add form layout to the view
        add(formLayout);
    }

}
