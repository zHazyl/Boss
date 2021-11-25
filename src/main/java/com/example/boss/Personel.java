package com.example.boss;

import java.math.BigDecimal;
import java.sql.Date;

public class Personel {

    String id;
    String username;
    String password;
    String email;
    Date timeJoin;
    String firstName;
    String lastName;
    String phone;
    String position;
    String gender;
    Date born;
    String address;
    double salary;
    int dayOff;
    int dayLate;
    double bonus;

    public int getDayOff() {
        return dayOff;
    }

    public void setDayOff(int dayOff) {
        this.dayOff = dayOff;
    }

    public int getDayLate() {
        return dayLate;
    }

    public void setDayLate(int dayLate) {
        this.dayLate = dayLate;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTimeJoin() {
        return timeJoin;
    }

    public void setTimeJoin(Date timeJoin) {
        this.timeJoin = timeJoin;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Personel(String id, String username, String password, String email, Date timeJoin, String firstName, String lastName, String phone, String position, String gender, Date born, String address, double salary, int dayOff, int dayLate, double bonus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.timeJoin = timeJoin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.position = position;
        this.gender = gender;
        this.born = born;
        this.address = address;
        this.salary = salary;
        this.dayOff = dayOff;
        this.dayLate = dayLate;
        this.bonus = bonus;
    }
}
