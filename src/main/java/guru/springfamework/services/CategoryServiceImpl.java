package guru.springfamework.services;

import java.util.List;
import java.util.stream.Collectors;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.repositories.CategoryRepository;

public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryMapper categoryMapper;
	private final CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryMapper categoryMapper,
			CategoryRepository categoryRepository) {
		super();
		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
	}

	public List<CategoryDTO> getAllCategories() {
		
		return categoryRepository.findAll()
			.stream()
			.map(categoryMapper::categoryToCategoryDTO)
			.collect(Collectors.toList());
	}

	public CategoryDTO getCategoryByName(String name) {
		return categoryMapper.categoryToCategoryDTO(
				categoryRepository.findByName(name));
	}

}
