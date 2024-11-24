package services.impl;

import DAO.CategoryDAO;
import DTO.request.CategoryRequest;
import DTO.response.CategoryResponse;
import Mapper.CategoryMapper;
import business.Category;
import services.ICategoryServices;

import java.util.Comparator;
import java.util.List;
public class CategoryServiceImpl implements ICategoryServices {

    private final CategoryDAO categoryDAO;
//     Constructor để inject CategoryDAO
    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAO();  // Khởi tạo CategoryDAO
    }

    @Override
    public List<CategoryResponse> getListCategory() {
        /*
        //todo
        // call dbutil, get list model, transfer model to dto, return dto
        List<Category> categories = categoryDAO.getCategoryList();
        categories.sort(Comparator.comparing(Category::getId));
        // Chuyển đổi sang DTO
        return categories.stream()
                .map(CategoryResponse::new).toList();

         */
        return  null;
    }

    @Override
    public CategoryResponse getCategoryById(Long id){
        Category category = categoryDAO.getCategoryByID(id);
        if (category == null) {
            return null;
        }
        return CategoryMapper.convertToDTO(category);
    }
    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        // Kiểm tra đầu vào của DTO nếu cần
        if (categoryRequest == null) {
            throw new IllegalArgumentException("CategoryDTO cannot be null");
        }
        categoryDAO.addCategory(CategoryMapper.convertToEntity(categoryRequest));
    }
    @Override
    public void updateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest == null) {
            throw new IllegalArgumentException("CategoryDTO cannot be null");
        }
        categoryDAO.editCategory(CategoryMapper.convertToEntity(categoryRequest));
    }
}
