package com.spring_security.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Possession{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Long getId() {
        System.out.println("getting the Id of entity");
        return id;
    }

    @Override
    public String toString() {
        return "Possession{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }
}