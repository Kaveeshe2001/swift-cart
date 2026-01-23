package swiftcart.service.category;

import org.springframework.stereotype.Service;
import swiftcart.repository.CategoryRepository;

@Service
public class CategoryService implements ICategoryService{
    private CategoryRepository categoryRepository;
}
