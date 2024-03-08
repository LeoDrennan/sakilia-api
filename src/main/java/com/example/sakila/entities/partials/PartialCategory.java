package com.example.sakila.entities.partials;

import com.example.sakila.entities.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
public class PartialCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Short id;

    @Column(name="name")
    private String name;

    public PartialCategory() {
    }

    public PartialCategory(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
