package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final String uploadDir = "src/main/resources/static/uploads/";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        // Tạo thư mục upload nếu chưa tồn tại
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Không thể tạo thư mục upload", e);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = saveImage(imageFile);
            product.setImage(imageName);
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.search(keyword);
    }

    private String saveImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;
        Path filePath = Paths.get(uploadDir + filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }
}
