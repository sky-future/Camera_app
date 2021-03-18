package model;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String firstName, lastName, email;
    List<Camera> cameras;


    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public User(){
        ArrayList<Camera> cameras = new ArrayList<Camera>();
    }

    //Vérifie si la caméra est déjà dans la liste, si ce n'est pas le cas on l'ajoute.
    //La vérification se fait à l'aide de l'ip de la caméra.
    public void addCamera(Camera camera){
            for (Camera cam : cameras){
                if(cam.ipCamera == camera.ipCamera){
                    return;
                }
                else{
                    cameras.add(camera);
                }
            }
    }

    public void removeCamera(Camera camera){
        cameras.remove(camera);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
