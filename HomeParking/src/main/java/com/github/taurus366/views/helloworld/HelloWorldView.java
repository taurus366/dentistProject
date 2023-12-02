package com.github.taurus366.views.helloworld;

import com.github.taurus366.model.entity.UserEntity;
import com.github.taurus366.model.service.UserService;
import com.github.taurus366.security.AuthenticatedUser;
import com.github.taurus366.views.MainLayout;
import com.storedobject.chart.*;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.RolesAllowed;
import org.system.i18n.CustomI18NProvider;
import org.system.i18n.model.dto.LanguageDTO;
import org.system.i18n.service.LanguageService;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "hello", layout = MainLayout.class)
//@PermitAll
@RolesAllowed("ADMIN")
//@AnonymousAllowed
public class HelloWorldView extends VerticalLayout {

    private TextField name;
    private Button sayHello;
    private String userLocale;

    public HelloWorldView(LanguageService languageService, AuthenticatedUser authenticatedUser, UserService userService) {
        Optional<UserEntity> maybeUser2 = authenticatedUser.get();
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
//        setVerticalComponentAlignment(Alignment.END, name, sayHello);


        CustomI18NProvider provider = new CustomI18NProvider(languageService);

        maybeUser2.ifPresent(maybeUser -> {
            userLocale = maybeUser.getLocale().toUpperCase();
            System.out.println(Locale.of(maybeUser.getLocale().toUpperCase()));
        name = new TextField(provider.getTranslation("greeting", Locale.of(maybeUser.getLocale()), "ALI"));
        });

        maybeUser2.ifPresent(maybeUser -> {
        final ComboBox<LanguageDTO> languageSelectorBox = provider.getLanguageSelectorBox(maybeUser.getLocale(), provider.getTranslation("select_language", Locale.of(maybeUser.getLocale()), "ALI"));
            languageSelectorBox.addValueChangeListener(event -> {
                System.out.println(event);

                maybeUser.setLocale(event.getValue().getLocale());
                userService.update(maybeUser);
                UI.getCurrent().getPage().reload();
            });
            add(languageSelectorBox);

        });

        add(name, sayHello);



    }

}
