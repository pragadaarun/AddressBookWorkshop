package com.bridgelabz.addressbook.database;

import java.time.LocalDate;
import java.util.Objects;

public class AddressBookData {

    public int id;
    public int personId;
    public int typeId;
    public String type;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String city;
    public String state;
    public String zip;
    public String address;
    public LocalDate date_added;


    public AddressBookData(int id, String first_name, String last_name, String address, String city,
                           String state, String zip, String phone_number, String email) {

        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phone_number;
        this.email = email;
    }


    public AddressBookData( int personId, int typeId, String firstName, String lastName, String phoneNumber,
                           String email, String city, String state, String zip,String address, LocalDate date_added) {

        this.personId = personId;
        this.typeId = typeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.address=address;
        this.date_added = date_added;
    }


    public AddressBookData(String type, String first_name, String last_name, String phone_number, String email, String city, String state, String zip, String address, LocalDate date_added) {

        this.type = type;
        firstName = first_name;
        lastName = last_name;
        phoneNumber = phone_number;
        this.email = email;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.address = address;
        this.date_added = date_added;
    }

    @Override
    public String toString() {
        return "AddressBookData{" +
                "personId=" + personId +
                ", typeId=" + typeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", address='" + address + '\'' +
                ", date_added=" + date_added +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressBookData)) return false;
        AddressBookData that = (AddressBookData) o;
        return personId == that.personId &&
                typeId == that.typeId &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(email, that.email) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(address, that.address) &&
                Objects.equals(date_added, that.date_added);
    }

}
