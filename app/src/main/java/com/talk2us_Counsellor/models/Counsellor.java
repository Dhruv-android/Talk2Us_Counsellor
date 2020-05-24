package com.talk2us_Counsellor.models;

public class Counsellor {
    public Boolean available;
    public int clients;
    public String id;
    public Boolean status_confirmed;


    public Boolean getStatus_confirmed() {
        return status_confirmed;
    }

    public void setStatus_confirmed(Boolean status_confirmed) {
        this.status_confirmed = status_confirmed;
    }
    public Counsellor(){
        available=true;
        clients=0;
        id="Not available";
        status_confirmed=false;
    }
    public Counsellor(Boolean available, int clients, String id,Boolean status_confirmed) {
        this.available = available;
        this.clients = clients;
        this.id = id;
        this.status_confirmed=status_confirmed;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public int getClients() {
        return clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
