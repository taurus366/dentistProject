package com.github.taurus366.views.client;

import com.github.taurus366.security.AuthenticatedUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@AnonymousAllowed
@PageTitle("Home")
@Route(value = "home")
@RouteAlias(value = "")
public class IndexPageView extends VerticalLayout implements BeforeEnterObserver {
    private final AuthenticatedUser authenticatedUser;
    public IndexPageView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        H2 g = new H2("test");
        Button button = new Button("CLICK ME TO REDIREC TO LOGIN");

        button.addClickListener(event -> {

           getUI().ifPresent(ui -> {

               ui.navigate("login");
           });
        });

        add(g, button);

    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            event.forwardTo("user");
        }
    }
}
