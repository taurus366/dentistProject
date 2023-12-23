package org.system.i18n;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.StyleSheet;

@Tag("div")
@StyleSheet("./themes/homeparking/flag-icons/css/flag-icons.css")
//@CssImport("../resources/flag-icons/css/flag-icons.css")
//@StyleSheet("context://flag-icons.css")
//@CssImport(value = "./themes/homeparking/flag-icons/css/flag-icons.css")
public class CountryFlagT extends Component implements HasSize, ClickNotifier<CountryFlagT> {

    private String country = null;
    private Boolean bigger = true;

    public CountryFlagT() {
        setWidth("100px"); // 50px
        setHeight("99px"); // 49px
        setClassName("flag-icon-background");
        addClassName("fi");
        getStyle().set("margin-left", "5px");
    }

    public CountryFlagT(String country) {
        this();
        this.country = country;
        setClass();
        setBigger();
    }

    public CountryFlagT(String country, boolean bigger){
        this();
        this.bigger = bigger;
        this.country = country;
        setClass();
        if(!bigger) setSmaller();
        else setBigger();
    }

    public void setSmaller() {
        removeClassName("fis");
        setWidth("50px");
        setHeight("49px");
    }

    public void setBigger() {
        addClassName("fis");
        setWidth("100px");
        setHeight("99px");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        removeClassName("fi-" + this.country);
        addClassName("fi-" + country);
        this.country = country;
    }

    private void setClass() {
        addClassName("fi-" + country);
    }

    public Boolean isBigger() {
        return bigger;
    }
}
