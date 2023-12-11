package com.example.application.views;

import com.example.application.views.about.AboutView;
import com.example.application.views.helloworld.HelloWorldView;
import com.example.application.views.myview.MyViewView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;
import com.vaadin.flow.component.button.Button;

import java.awt.*;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;
    private Button toggleTheme;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

            toggleTheme = new Button(VaadinIcon.SUN_RISE.create(), click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            System.out.println("theme list:::: " + themeList);
            if (themeList.contains(Lumo.DARK)) {
                toggleTheme.setIcon(VaadinIcon.MOON.create());
                themeList.remove(Lumo.DARK);
                themeList.add(Lumo.LIGHT);
            } else {
                themeList.add(Lumo.DARK);
                themeList.remove(Lumo.LIGHT);
                toggleTheme.setIcon(VaadinIcon.SUN_RISE.create());
            }

        });
        toggleTheme.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        toggleTheme.addClassNames(LumoUtility.JustifyContent.END);

        addToNavbar(true, toggle, viewTitle, toggleTheme);
    }

    private void addDrawerContent() {
        H1 appName = new H1("THEME");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
        nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));
        nav.addItem(new SideNavItem("My View", MyViewView.class, LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

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
}
