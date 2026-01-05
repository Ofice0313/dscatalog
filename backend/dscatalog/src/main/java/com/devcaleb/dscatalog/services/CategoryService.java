package com.devcaleb.dscatalog.services;

import com.devcaleb.dscatalog.dto.CategoryDTO;
import com.devcaleb.dscatalog.entity.Category;
import com.devcaleb.dscatalog.repositories.CategoryRepository;
import com.devcaleb.dscatalog.services.exceptions.DatabaseException;
import com.devcaleb.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable) {
        Page<Category> list = repository.findAll(pageable);
        return list.map(x -> new CategoryDTO(x));

    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Integer id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category category = new Category();
        copyDtoToEntity(dto, category);
        category = repository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Integer id, CategoryDTO dto) {
        try {
            Category entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new CategoryDTO(entity);
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

    private void copyDtoToEntity(CategoryDTO dto, Category category) {
        category.setName(dto.getName());
    }
}
