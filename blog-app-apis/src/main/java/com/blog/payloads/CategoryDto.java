package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
private Integer categoryId;
@NotBlank
@Size(min=4,message="tile must be in 4 character!!!!!")
private String categoryTitle;
@NotBlank
@Size(min=10,message="desp must be in 10 character!!!!!")
private String catagoryDescription;

}
