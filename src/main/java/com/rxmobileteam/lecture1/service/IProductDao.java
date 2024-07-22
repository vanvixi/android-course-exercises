package com.rxmobileteam.lecture1.service;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface IProductDao {
    boolean add(@NotNull Product product);


    Set<Product> findAll();
}
