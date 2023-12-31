package com.github.taurus366.views;

import com.github.taurus366.model.entity.UserEntity;
import com.github.taurus366.security.AuthenticatedUser;
import com.github.taurus366.views.about.AboutView;
import com.github.taurus366.views.settings.PriceListGroupSettingsView;
import com.github.taurus366.views.settings.PriceListSettingsView;
import com.github.taurus366.views.stats.StatsView;
import com.github.taurus366.views.user.UserListView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;


import jakarta.annotation.security.PermitAll;
import org.system.i18n.CustomI18NProvider;
import org.system.i18n.service.LanguageCustomerService;
import org.system.i18n.service.LanguageService;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
@Route(value = "user")
@PermitAll
public class MainLayout extends AppLayout implements RouterLayout, BeforeEnterObserver{

    private H2 viewTitle;
    private String userLocale;


    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;
    private CustomI18NProvider languageProvider;


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String parameterValue = event.getLocation().getPath();
        if(parameterValue.equals("user")){
            event.forwardTo("stats");
        }
    }

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, LanguageService languageService, LanguageCustomerService languageCustomerService) {
        Optional<UserEntity> optionalUser = authenticatedUser.get();
        optionalUser.ifPresent(userEntity -> userLocale = userEntity.getLocale().toUpperCase());
        languageProvider = new CustomI18NProvider(languageService, languageCustomerService);

        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        // Add the bell icon to the header
        Span numberOfNotifications = new Span("4");
        numberOfNotifications.getElement().getThemeList().addAll(
                Arrays.asList("badge", "error", "primary", "small", "pill"));
        numberOfNotifications.getStyle().set("position", "absolute")
                .set("transform", "translate(-40%, -85%)");


//        Icon bellIcon = VaadinIcon.BELL_O.create();

//        bellIcon.getElement().getStyle().set("")
//        bellIcon.setClassName("bell-icon");
//        bellIcon.addClickListener(e -> {
//            Notification.show("You have new notifications!");
//        });
//        bellIcon.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        bellIcon.getElement().appendChild(numberOfNotifications.getElement());

        Button bellBtn = new Button(VaadinIcon.BELL_O.create());
        bellBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        bellBtn.getElement().appendChild(numberOfNotifications.getElement());
        bellBtn.getElement().getStyle().set("flex-grow", "1");

        Div sampleNotification = new Div(new Text("Show notifications here"));
        sampleNotification.getStyle().set("padding", "var(--lumo-space-l)");

        ContextMenu menu = new ContextMenu();
        menu.setOpenOnClick(true);
        menu.setTarget(bellBtn);
        menu.add(sampleNotification);

        Div spacer = new Div();
        spacer.getStyle().set("flex-grow", "35");

        addToNavbar(true, toggle, viewTitle, spacer, bellBtn);
    }

    private void addDrawerContent() {

        final String title = languageProvider.getTranslation("Dentist", Locale.of(userLocale));

        H1 appName = new H1(title);
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);
        header.getStyle().set("box-shadow", "1px 2px 20px 0px");

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());

        // Set the layout for the "camera-list" route



    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        if (accessChecker.hasAccess(AboutView.class)) {
            nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));

        }

        if(accessChecker.hasAccess(UserListView.class)) {
//            nav.addItem(new SideNavItem("User list", UserListView.class, LineAwesomeIcon.USERS_SOLID.create()));
            final String userListTitle = languageProvider.getTranslation("userList", Locale.of(userLocale));
//            SideNavItem userNavItem = new SideNavItem(userListTitle);
//                userNavItem.setPrefixComponent(VaadinIcon.USERS.create());

            // Add items to the nested menu
//            userNavItem.addItem(new SideNavItem("User list", UserListView.class, LineAwesomeIcon.USERS_SOLID.create()));

//            nav.addItem(userNavItem);
            final SideNavItem sideNavItem = new SideNavItem(userListTitle, UserListView.class, LineAwesomeIcon.USERS_SOLID.create());
            makeHoverLeaveEffect(sideNavItem);
            nav.addItem(sideNavItem);
        }

        if(accessChecker.hasAccess(StatsView.class)) {
            final String title = languageProvider.getTranslation("Stats", Locale.of(userLocale));
            final SideNavItem sideNavItem = new SideNavItem(title, StatsView.class, LineAwesomeIcon.SASS.create());
            makeHoverLeaveEffect(sideNavItem);
            nav.addItem(sideNavItem);
        }


        final String settingsTitle = languageProvider.getTranslation("Settings", Locale.of(userLocale));
        /// SETTINGS
        SideNavItem settingsNavItem = new SideNavItem(settingsTitle);
//        makeHoverLeaveEffect(settingsNavItem);
        settingsNavItem.setPrefixComponent(VaadinIcon.OPTIONS.create());
        if(accessChecker.hasAccess(PriceListSettingsView.class)){
            final String priceListTitle = languageProvider.getTranslation("PriceList", Locale.of(userLocale));
            final SideNavItem sideNavItem = new SideNavItem(priceListTitle, PriceListSettingsView.class, LineAwesomeIcon.DOLLAR_SIGN_SOLID.create());
            makeHoverLeaveEffect(sideNavItem);
            settingsNavItem.addItem(sideNavItem);
        }
        if(accessChecker.hasAccess(PriceListGroupSettingsView.class)){
            final String priceListGroupTitle = languageProvider.getTranslation("PriceListGroup", Locale.of(userLocale));
            final SideNavItem sideNavItem = new SideNavItem(priceListGroupTitle, PriceListGroupSettingsView.class, LineAwesomeIcon.OBJECT_GROUP.create());
            makeHoverLeaveEffect(sideNavItem);
            settingsNavItem.addItem(sideNavItem);
        }

        nav.addItem(settingsNavItem);

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<UserEntity> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            UserEntity userEntity = maybeUser.get();

            Avatar avatar = new Avatar(userEntity.getName());
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(userEntity.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(userEntity.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            final String profileTitle = languageProvider.getTranslation("Profile", Locale.of(userLocale));
            userName.getSubMenu().addItem(profileTitle, e -> {
                getUI().ifPresent(ui -> ui.navigate("profile"));
            });
           final String signOutTitle = languageProvider.getTranslation("SignOut", Locale.of(userLocale));
            userName.getSubMenu().addItem(signOutTitle, e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    private void makeHoverLeaveEffect(SideNavItem sideNavItem) {
        sideNavItem.getElement().addEventListener("mouseover", event -> {
            sideNavItem.getStyle().set("padding-left", "10px");
        });
        sideNavItem.getElement().addEventListener("mouseleave", event -> {
            sideNavItem.getStyle().remove("padding-left");
        });
    }
}
