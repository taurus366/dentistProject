package org.parking.system.views.door;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;


@PageTitle("Door")
@Route(value = "door")
@RolesAllowed({"USER", "ADMIN"})
public class DoorView extends VerticalLayout {


    public DoorView() {
        Image closeDoorImg = new Image("icon/garage-door-closed.png", "Closed door Logo");
        Image openDoorImg = new Image("icon/garage-door-open.png", "Open door Logo");
        Image testImg = new Image("icon/gate-test.jpg", "Open door Logo");
        testImg.setWidth("30%");





        closeDoorImg.getStyle().set("width", "50px");
        openDoorImg.getStyle().set("width", "50px");

        Button openDoor = new Button(openDoorImg);
        Button closeDoor = new Button(closeDoorImg);

        setStyleToBtns(openDoor, closeDoor);

       openDoor.addClickListener(e1 -> {
           System.out.println("Open Door");
           remove(openDoor);
           add(closeDoor);

       });

         closeDoor.addClickListener(e -> {
              System.out.println("Close Door");
                remove(closeDoor);
                add(openDoor);
         });


//          setMargin(true);
          setAlignItems(Alignment.CENTER);
//          setSizeFull();

        H1 h1 = new H1("Control Door");

          add(testImg, h1);







        if (checkDoorStatus()) {
            add(openDoor);
        } else {
            add(closeDoor);
        }

    }

    private void setStyleToBtns(Button openDoor, Button closeDoor) {
        String buttonStyle = "width: 107px; height: 100px; border-radius: 50px; background-color: #3498db; color: white; font-size: 18px; font-weight: bold;";


        openDoor.getElement().setAttribute("style", buttonStyle);
        closeDoor.getElement().setAttribute("style", buttonStyle + " background-color: #e74c3c;");


    }

    private boolean checkDoorStatus() {
        return false;
    }

}
