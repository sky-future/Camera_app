package model;

import java.util.Objects;

public class Camera {

    public String ipCamera;
    public boolean gpio12, gpio13, gpio14, gpio15, gpio16;
    public String gpio12Name, gpio13Name, gpio14Name, gpio15Name, gpio16Name;



    public Camera(String ipCamera, boolean gpio12, boolean gpio13, boolean gpio14, boolean gpio15, boolean gpio16, String gpio12Name, String gpio13Name, String gpio14Name, String gpio15Name, String gpio16Name) {
        this.ipCamera = ipCamera;
        this.gpio12 = gpio12;
        this.gpio13 = gpio13;
        this.gpio14 = gpio14;
        this.gpio15 = gpio15;
        this.gpio16 = gpio16;
        this.gpio12Name = gpio12Name;
        this.gpio13Name = gpio13Name;
        this.gpio14Name = gpio14Name;
        this.gpio15Name = gpio15Name;
        this.gpio16Name = gpio16Name;
    }

    public Camera() {
    }

    @Override
    public String toString() {
        return "Camera{" + ipCamera +
                "gpio12=" + gpio12 +
                ", gpio13=" + gpio13 +
                ", gpio14=" + gpio14 +
                ", gpio15=" + gpio15 +
                ", gpio16=" + gpio16 +
                ", gpio12Name='" + gpio12Name + '\'' +
                ", gpio13Name='" + gpio13Name + '\'' +
                ", gpio14Name='" + gpio14Name + '\'' +
                ", gpio15Name='" + gpio15Name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return ipCamera.equals(camera.ipCamera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipCamera);
    }

    public String getIpCamera() {
        return ipCamera;
    }

    public void setIpCamera(String ipCamera) {
        this.ipCamera = ipCamera;
    }

    public void setGpio12(boolean gpio12) {
        this.gpio12 = gpio12;
    }

    public void setGpio13(boolean gpio13) {
        this.gpio13 = gpio13;
    }

    public void setGpio14(boolean gpio14) {
        this.gpio14 = gpio14;
    }

    public void setGpio15(boolean gpio15) {
        this.gpio15 = gpio15;
    }

    public void setGpio16(boolean gpio16) {
        this.gpio16 = gpio16;
    }

    public void setGpio12Name(String gpio12Name) {
        this.gpio12Name = gpio12Name;
    }

    public void setGpio13Name(String gpio13Name) {
        this.gpio13Name = gpio13Name;
    }

    public void setGpio14Name(String gpio14Name) {
        this.gpio14Name = gpio14Name;
    }

    public void setGpio15Name(String gpio15Name) {
        this.gpio15Name = gpio15Name;
    }

    public boolean isGpio12() {
        return gpio12;
    }

    public boolean isGpio13() {
        return gpio13;
    }

    public boolean isGpio14() {
        return gpio14;
    }

    public boolean isGpio15() {
        return gpio15;
    }

    public boolean isGpio16() {
        return gpio16;
    }

    public String getGpio12Name() {
        return gpio12Name;
    }

    public String getGpio13Name() {
        return gpio13Name;
    }

    public String getGpio14Name() {
        return gpio14Name;
    }

    public String getGpio15Name() {
        return gpio15Name;
    }

    public String getGpio16Name() {
        return gpio16Name;
    }

    public void setGpio16Name(String gpio16Name) {
        this.gpio16Name = gpio16Name;
    }
}
