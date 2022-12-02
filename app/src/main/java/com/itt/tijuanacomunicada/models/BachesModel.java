package com.itt.tijuanacomunicada.models;

public class BachesModel {
    private String latitud;
    private String longitud;
    private String photo;
    private String email;

    public BachesModel() {}

    public BachesModel(String latitud, String longitud, String photo, String email) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.photo = photo;
        this.email = email;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
