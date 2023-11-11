package org.parking.system.views.camera;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.parking.system.model.entity.Camera;
import org.parking.system.model.service.CameraRepository;
import org.parking.system.model.service.CameraService;
import org.springframework.cglib.transform.impl.FieldProvider;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.CheckBoxGroupProvider;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;


@PageTitle("Camera list")
@Route(value = "camera_list")
@RolesAllowed("ADMIN")
public class CameraListView extends HorizontalLayout {

    private CameraService cameraService;
    public CameraListView(CameraService cameraService) {
        this.cameraService = cameraService;

        GridCrud<Camera> crud = new GridCrud<>(Camera.class);

        crud.getGrid().removeColumns( crud.getGrid().getColumnByKey("password"),
        crud.getGrid().getColumnByKey("port"),
        crud.getGrid().getColumnByKey("rtspUrl"),
                crud.getGrid().getColumnByKey("modified"),
                crud.getGrid().getColumnByKey("created"),
                 crud.getGrid().getColumnByKey("ip"));



        final CrudFormFactory<Camera> crudFormFactory = crud.getCrudFormFactory();
//        crudFormFactory.setFieldType("port", IntegerField.class);
//        crudFormFactory.setFieldType("Port", IntegerField.class);
//        crudFormFactory.setFieldType( CrudOperation.ADD ,"Port", IntegerField.class);
        crudFormFactory.setDisabledProperties("id");


        crudFormFactory.setUseBeanValidation(true);

        crudFormFactory.setVisibleProperties(CrudOperation.ADD, "name", "ip", "port", "username", "password", "rtspUrl", "type", "activated");

//        crud.setCrudFormFactory(crudFormFactory);


        // Create a custom renderer for the LocalDate (assuming "created" is of type LocalDate)
        TextRenderer<Camera> cameraTextRendererDateCreated = new TextRenderer<>(
                camera -> {
                    Date myDate = Date.from(camera.getCreated());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    return formatter.format(myDate);
                }
        );

        final Grid<Camera> grid = crud.getGrid();
      grid.addColumn(createCameraTextRendererDateCreated("")).setHeader("Created").setKey("created");
      grid.addColumn(createCameraTextRendererDateCreated("modified")).setHeader("Modified").setKey("modified");



        crud.getGrid().setItems(cameraService.findAll());
            crud.setCrudListener(new CrudListener<>() {
                @Override
                public Collection<Camera> findAll() {
                    return cameraService.findAll();
                }

                @Override
                public Camera add(Camera camera) {
                    return cameraService.save(camera);
                }

                @Override
                public Camera update(Camera camera) {
                    return cameraService.update(camera);
                }

                @Override
                public void delete(Camera camera) {
                    cameraService.delete(camera.getId());
                }
            });

        add(crud);




    }

    public TextRenderer<Camera> createCameraTextRendererDateCreated(String type) {
        return new TextRenderer<>(
                camera -> {
                    Date myDate = Date.from(camera.getCreated());
                    if(type.equals("modified")) {
                        myDate = Date.from(camera.getModified());
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    return formatter.format(myDate);
                }
        );
    }

}
