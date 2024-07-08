package com.rxmobileteam.lecture1.service;

import com.rxmobileteam.lecture1.data.ProductDao;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link ProductService} provides an API that allows to manage {@link Product}s.
 * <p>
 * TODO: 1. Using {@link ProductDao} implement method {@link ProductService#addProduct(Product)}}
 * TODO: 2. Using {@link ProductDao} implement method {@link ProductService#searchProducts(String)}
 */
public class ProductService {

    public ProductService(IProductDao productDao) {
        this.productDao = productDao;
    }

    final IProductDao productDao;


    /**
     * Adds a new product to the system.
     *
     * @param product a product to add
     * @return {@code true} if a product was added, {@code false} otherwise.
     */
    public boolean addProduct(@NotNull Product product) {
        return productDao.add(product);
    }

    /**
     * Returns all products that contains the given query in the name or description.
     *
     * @param query a search query
     * @return a list of found products
     */
    public List<Product> searchProducts(String query) {
        final Set<Product> productSet = productDao.findAll();

        if (query == null || query.isEmpty()) {
            return new ArrayList<>(productSet);
        }

        return productSet.stream().filter(
            p -> p.getName().contains(query) || p.getDescription().contains(query)
        ).collect(Collectors.toList());
    }
}
