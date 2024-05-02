package com.productapp.productapp.service;

import com.productapp.productapp.model.User;
import jakarta.persistence.*;

import java.util.List;

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<ProductLine> productLines;
}
