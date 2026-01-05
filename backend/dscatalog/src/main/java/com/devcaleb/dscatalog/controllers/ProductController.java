package com.devcaleb.dscatalog.controllers;

import com.devcaleb.dscatalog.dto.ProductDTO;
import com.devcaleb.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/products")
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
         Page<ProductDTO> list = service.findAllPaged(pageable);
         return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }


    @PostMapping(value = "/products")
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
