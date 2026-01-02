package com.devcaleb.dscatalog.services;

import com.devcaleb.dscatalog.dto.CategoryDTO;
import com.devcaleb.dscatalog.entity.Category;
import com.devcaleb.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

    }
}
