package swiftcart.service.image;

import org.springframework.stereotype.Service;
import swiftcart.repository.ImageRepository;

@Service
public class ImageService implements IImageService{
    private ImageRepository imageRepository;
}
