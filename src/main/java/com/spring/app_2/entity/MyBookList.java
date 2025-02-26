package com.spring.app_2.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="MyBooks")
public class MyBookList {

    @Id
    private int id;
    private String name;
    private String author;
    private String country;
    public MyBookList() {
        super();
        // TODO Auto-generated constructor stub
    }
    public MyBookList(int id, String name, String author, String country) {
        super();
        this.id = id;
        this.name = name;
        this.author = author;
        this.country = country;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

}
