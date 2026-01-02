package com.devcaleb.dscatalog.controllers;

import com.devcaleb.dscatalog.dto.CategoryDTO;
import com.devcaleb.dscatalog.entity.Category;
import com.devcaleb.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping(value = "/categories")
     public ResponseEntity<List<CategoryDTO>> findAll() {
         List<CategoryDTO> list = service.findAll();
         return ResponseEntity.ok().body(list);
     }
}
