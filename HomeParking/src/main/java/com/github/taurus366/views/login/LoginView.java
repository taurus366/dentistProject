package com.github.taurus366.views.login;

import com.github.taurus366.security.AuthenticatedUser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.WebBrowser;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.firitin.util.WebStorage;


import javax.swing.*;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")

public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;

    public LoginView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("DENTIST");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        i18n.setAdditionalInformation(null);

        setI18n(i18n);
        setForgotPasswordButtonVisible(false);
        setOpened(true);


    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            setOpened(false);
//            UI.getCurrent().getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
            event.forwardTo("user");
//            event.forwardTo("stats");

        }



        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }


}
