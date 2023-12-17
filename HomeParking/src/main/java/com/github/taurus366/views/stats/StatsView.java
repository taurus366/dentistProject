package com.github.taurus366.views.stats;


import com.github.taurus366.model.entity.UserEntity;
import com.github.taurus366.security.AuthenticatedUser;
import com.github.taurus366.views.MainLayout;
import com.storedobject.chart.*;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;
import org.system.i18n.CustomI18NProvider;
import org.system.i18n.service.LanguageCustomerService;
import org.system.i18n.service.LanguageService;

import java.util.Locale;
import java.util.Optional;

@PageTitle("Statistic")
@Route(value = "stats", layout = MainLayout.class)
@RouteAlias(value = "stats", layout = MainLayout.class)
@PermitAll
public class StatsView extends VerticalLayout {

    private String userLocale;


    public StatsView(LanguageService languageService, AuthenticatedUser authenticatedUser, LanguageCustomerService languageCustomerService) {
        Optional<UserEntity> optionalUser = authenticatedUser.get();
        optionalUser.ifPresent(userEntity -> userLocale = userEntity.getLocale().toUpperCase());

        setMargin(true);

        CustomI18NProvider languageProvider = new CustomI18NProvider(languageService, languageCustomerService);

        String January = languageProvider.getTranslation("January", Locale.of(userLocale));
        String February = languageProvider.getTranslation("February", Locale.of(userLocale));
        String March = languageProvider.getTranslation("March", Locale.of(userLocale));
        String April = languageProvider.getTranslation("April", Locale.of(userLocale));
        String May = languageProvider.getTranslation("May", Locale.of(userLocale));
        String June = languageProvider.getTranslation("June", Locale.of(userLocale));
        String July = languageProvider.getTranslation("July", Locale.of(userLocale));
        String August = languageProvider.getTranslation("August", Locale.of(userLocale));
        String September = languageProvider.getTranslation("September", Locale.of(userLocale));
        String October = languageProvider.getTranslation("October", Locale.of(userLocale));
        String November = languageProvider.getTranslation("November", Locale.of(userLocale));
        String December = languageProvider.getTranslation("December", Locale.of(userLocale));

        // Let us define some inline data.
        CategoryData labels = new CategoryData(January, February, March, April, May, June, July, August, September, October, November, December);
        Data data = new Data(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        String visitors = languageProvider.getTranslation("SiteVisitors", Locale.of(userLocale));
        H2 h2VisitorsTitle = new H2(visitors);
//        Title title = new Title(visitors);
//        Position pTitle = new Position();
//        pTitle.setBottom(Size.percentage(0));
//        title.setPosition(pTitle);

        // Creating a chart display area.
        SOChart soChart = new SOChart();
        soChart.setSize("98%", "600px");
// We are going to create a couple of charts. So, each chart should be positioned
// appropriately.
// Create a self-positioning chart.
        NightingaleRoseChart nc = new NightingaleRoseChart(labels, data);
        Position p = new Position();
        p.setLeft(Size.percentage(50));
        nc.setPosition(p); // Position it leaving 50% space at the top

        // Second chart to add.
        BarChart bc = new BarChart(labels, data);
        RectangularCoordinate rc;
        rc = new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        p = new Position();
        p.setRight(Size.percentage(50));
        rc.setPosition(p); // Position it leaving 55% space at the bottom
        bc.plotOn(rc); // Bar chart needs to be plotted on a coordinate system

//        Toolbox toolbox = new Toolbox();
//        toolbox.addButton(new Toolbox.Download(), new Toolbox.Zoom(), new Toolbox.DataView());
// Add the chart components to the chart display area.
        soChart.add(nc, bc);

        add(h2VisitorsTitle, soChart);

        //        // Creating a chart display area
//        SOChart soChart = new SOChart();
//        soChart.setSize("800px", "500px");
//
//// Generating some random values for a LineChart
//        Random random = new Random();
//        Data xValues = new Data(), yValues = new Data();
//        for(int x = 0; x < 40; x++) {
//            xValues.add(x);
//            yValues.add(random.nextDouble());
//        }
//        xValues.setName("X Values");
//        yValues.setName("Random Values");
//
//// Line chart is initialized with the generated XY values
//        LineChart lineChart = new LineChart(xValues, yValues);
//        lineChart.setName("40 Random Values");
//
//// Line chart needs a coordinate system to plot on
//// We need Number-type for both X and Y axes in this case
//        XAxis xAxis = new XAxis(DataType.NUMBER);
//        YAxis yAxis = new YAxis(DataType.NUMBER);
//        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
//        lineChart.plotOn(rc);
//
//// Add to the chart display area with a simple title
//        soChart.add(lineChart, new Title("Sample Line Chart"));
//
//// Add to my layout
//        add(soChart);

//        // Creating a chart display area
//        SOChart soChart = new SOChart();
//        soChart.setSize("600px", "650px");
//
//// Generating 10 set of values for 10 LineCharts for the equation:
//// y = a + a * x / (a - 11) where a = 1 to 10, x and y are positive
//        LineChart[] lineCharts = new LineChart[10];
//        Data[] xValues = new Data[lineCharts.length];
//        Data[] yValues = new Data[lineCharts.length];
//        int i;
//        for(i = 0; i < lineCharts.length; i++) {
//            xValues[i] = new Data();
//            xValues[i].setName("X (a = " + (i + 1) + ")");
//            yValues[i] = new Data();
//            yValues[i].setName("Y (a = " + (i + 1) + ")");
//        }
//// For each line chart, we need only 2 end-points (because they are straight lines).
//        int a;
//        for(i = 0; i < lineCharts.length; i++) {
//            a = i + 1;
//            xValues[i].add(0);
//            yValues[i].add(a);
//            xValues[i].add(11 - a);
//            yValues[i].add(0);
//        }
//
//// Line charts are initialized here
//        for(i = 0; i < lineCharts.length; i++) {
//            lineCharts[i] = new LineChart(xValues[i], yValues[i]);
//            lineCharts[i].setName("a = " + (i + 1));
//        }
//
//// Line charts need a coordinate system to plot on
//// We need Number-type for both X and Y axes in this case
//        XAxis xAxis = new XAxis(DataType.NUMBER);
//        YAxis yAxis = new YAxis(DataType.NUMBER);
//        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
//        for(i = 0; i < lineCharts.length; i++) {
//            lineCharts[i].plotOn(rc);
//            soChart.add(lineCharts[i]); // Add the chart to the display area
//        }
//
//// Add a simple title too
//        soChart.add(new Title("Equation: y = a + a * x / (a - 11) where a = 1 to 10, x and y are positive"));
//
//// We don't want any legends
//        soChart.disableDefaultLegend();
//
//// Add it to my layout
//        add(soChart);

//        // Creating a chart display area
//        SOChart soChart = new SOChart();
//        soChart.setSize("800px", "500px");
//
//// Tree chart
//// (By default it assumes circular shape. Otherwise, we can set orientation)
//// All values are randomly generated
//        TreeChart tc = new TreeChart();
//        TreeData td = new TreeData("Root", 1000);
//        tc.setTreeData(td);
//        Random r = new Random();
//        for(int i = 1; i < 21; i++) {
//            td.add(new TreeData("Node " + i, r.nextInt(500)));
//        }
//        TreeData td1 = td.get(13);
//        td = td.get(9);
//        for(int i = 50; i < 56; i++) {
//            td.add(new TreeData("Node " + i, r.nextInt(500)));
//        }
//        for(int i = 30; i < 34; i++) {
//            td1.add(new TreeData("Node " + i, r.nextInt(500)));
//        }
//
//// Add to the chart display area with a simple title
//        soChart.add(tc, new Title("A Circular Tree Chart"));
//
//// Finally, add it to my layout
//        add(soChart);

    }
}
