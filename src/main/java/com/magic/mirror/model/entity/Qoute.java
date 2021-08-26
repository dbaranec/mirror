package com.magic.mirror.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QOUTE")
public class Qoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "QOUTE_TEXT", unique = true)
    private String qouteText;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQouteText() {
        return qouteText;
    }

    public void setQouteText(String qouteText) {
        this.qouteText = qouteText;
    }
}
