package com.ebay.codechallenge.main.model;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long categoryId;
    @Column(name = "name")
    private String categoryName;
    @Column(name = "preapproved")
    private boolean preapproved;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isPreapproved() {
        return preapproved;
    }

    public void setPreapproved(boolean preapproved) {
        this.preapproved = preapproved;
    }
}
