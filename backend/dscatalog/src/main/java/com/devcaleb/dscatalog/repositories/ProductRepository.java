package com.devcaleb.dscatalog.repositories;

import com.devcaleb.dscatalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
