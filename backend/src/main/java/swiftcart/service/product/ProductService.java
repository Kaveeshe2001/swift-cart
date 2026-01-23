package swiftcart.service.product;

import org.springframework.stereotype.Service;
import swiftcart.model.Product;
import swiftcart.repository.ProductRepository;

@Service
public class ProductService implements IProductService {
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return null;
    }
}
