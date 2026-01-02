package com.devcaleb.dscatalog.dto;

import com.devcaleb.dscatalog.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Integer id;
    private String name;

    public CategoryDTO(Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
