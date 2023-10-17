package com.matthelium.birthdays;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User{
    public String name;
    public int id;
    public Date birthday;
    public String presents;

    public User(String name, Date birthday, String presents, int id){
        this.name = name;
        this.birthday = birthday;
        this.presents = presents;
        this.id = id;
    }

    public String textDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(birthday);
    }

    private boolean isOpened = false; // По умолчанию вложенный список закрыт

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }
}
