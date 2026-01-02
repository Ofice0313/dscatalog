package com.devcaleb.dscatalog.services;

import com.devcaleb.dscatalog.dto.CategoryDTO;
import com.devcaleb.dscatalog.dto.ProductDTO;
import com.devcaleb.dscatalog.entity.Category;
import com.devcaleb.dscatalog.entity.Product;
import com.devcaleb.dscatalog.repositories.CategoryRepository;
import com.devcaleb.dscatalog.repositories.ProductRepository;
import com.devcaleb.dscatalog.services.exceptions.DatabaseException;
import com.devcaleb.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(x -> new ProductDTO(x));

    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Integer id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Integer id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        }
        catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Integer id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Resource not found!");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
    }
}
