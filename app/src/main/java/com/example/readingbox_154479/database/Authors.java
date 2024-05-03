package com.example.readingbox_154479.database;

public class Authors {


    private String FirstName;
    private String Surname;
    private int BirthYear;
    private String Country;
    private String Photo;

    public Authors(String firstName, String surname, int birthYear, String country, String photo) {
        FirstName = firstName;
        Surname = surname;
        BirthYear = birthYear;
        Country = country;
        Photo = photo;
    }
    public Authors(){}

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public int getBirthYear() {
        return BirthYear;
    }

    public void setBirthYear(int birthYear) {
        BirthYear = birthYear;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
