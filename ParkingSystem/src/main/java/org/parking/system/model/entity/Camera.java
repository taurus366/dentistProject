package org.parking.system.model.entity;

import com.helger.commons.annotation.Nonempty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.parking.system.model.enums.CameraType;
import org.system.shared.model.entity.BaseEntity;

@Entity
@Table(name = "camera")
public class Camera extends BaseEntity {


     @Column(nullable = false)
     @NotNull
    private String name;

     @Column(nullable = false)
     @NotNull
    private String ip;

     @Column(nullable = false)
     @NotNull
    private int port;

     @Column(nullable = false)
     @NotNull
    private String username;

     @Column(nullable = false)
     @NotNull
    private String password;

     @Column(nullable = false)
    private String rtspUrl;     // rtsp://username:password@ip:port/cam/realmonitor?channel=1&subtype=0

     @Column(nullable = false)
     @NotNull
    private CameraType type;

     @Column(nullable = false)
    private Boolean isActivated = false;


    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRtspUrl() {
        return rtspUrl;
    }

    public void setRtspUrl(String rtspUrl) {
        this.rtspUrl = rtspUrl;
    }

    public CameraType getType() {
        return type;
    }

    public void setType(CameraType type) {
        this.type = type;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(Boolean activated) {
        isActivated = activated;
    }
}
