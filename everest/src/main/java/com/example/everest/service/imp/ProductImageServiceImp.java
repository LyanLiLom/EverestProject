package com.example.everest.service.imp;

import com.example.everest.dto.ProductDTO;
import com.example.everest.entity.ProductEntity;
import com.example.everest.entity.ProductImageEntity;
import com.example.everest.payload.request.ProductRequest;
import com.example.everest.repository.ProductImageRepository;
import com.example.everest.repository.ProductRepository;
import com.example.everest.service.FileStorageService;
import com.example.everest.service.ProductImageService;
import com.example.everest.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImp implements ProductImageService {
    @Value("${upload.file.path}")
    private String uploadDirectory;

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<ProductDTO> getAllProduct(String token,String role) {
        String tokenDecryp = token.substring(7);
        if (!tokenDecryp.isEmpty()){
        Claims decryptToken = jwtUtils.decryptTokenToClaims(tokenDecryp);
        Object roleObject = decryptToken.get("role");
        if (roleObject instanceof String roleDecryp){
            if (roleDecryp.equals(role)){
                List<ProductEntity> products = productRepository.findAll();
                return products.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
            }else {
                return Collections.emptyList();
            }
        }
        }
            throw new RuntimeException("Invalid role type or role not found in token");
    }

    @Override
    public ProductDTO convertToDTO(ProductEntity product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setTenSp(product.getName());
        productDTO.setGiaCu(product.getOldPrice());
        productDTO.setGiaMoi(product.getNewPrice());
        productDTO.setMoTa(product.getDescription());
        productDTO.setThongTinSp(product.getInformation());
        productDTO.setTonKho(product.getSku());
        productDTO.setImageUrl("https://localhost:6601/file/" + product.getProductImage().getImgName());
        return productDTO;
    }

    @Transactional
    @Override
    public void insertProduct(ProductRequest productRequest) {
        String fileName = fileStorageService.storeFile(productRequest.getMultipartFile());
        ProductEntity product = new ProductEntity();
        try {
            List<ProductImageEntity> list = productImageRepository.findAll();
            boolean flag = false;
            for (ProductImageEntity productImageEntity: list) {
                if (Objects.equals(productImageEntity.getImgName(),
                        productRequest.getMultipartFile().getOriginalFilename())){
                    product.setProductImage(productImageEntity);
                    flag = true;
                    break;
                }
            }

            if (!flag){
                ProductImageEntity newProductImage = new ProductImageEntity();
                newProductImage.setImgName(fileName);
                productImageRepository.save(newProductImage);
                product.setProductImage(newProductImage);
            }

            product.setName(productRequest.getProductName());
            product.setOldPrice(productRequest.getOldPrice());
            product.setNewPrice(productRequest.getNewPrice());
            product.setDescription(productRequest.getDescription());
            product.setInformation(productRequest.getInformation());
            product.setSku(productRequest.getSku());
            productRepository.save(product);
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to insert product: " + e.getMessage());
        };
    }
    @Transactional
    @Override
    public void updateProduct(ProductRequest productRequest) {
        int productId = productRequest.getId();

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " +productId));

        product.setName(productRequest.getProductName());
        product.setOldPrice(productRequest.getOldPrice());
        product.setNewPrice(productRequest.getNewPrice());
        product.setDescription(productRequest.getDescription());
        product.setInformation(productRequest.getInformation());
        product.setSku(productRequest.getSku());
        if (productRequest.getMultipartFile() != null){
            String fileName = fileStorageService.storeFile(productRequest.getMultipartFile());

            List<ProductImageEntity> list = productImageRepository.findAll();
            boolean flag = false;
            for (ProductImageEntity productImageEntity: list) {
                if (Objects.equals(productImageEntity.getImgName(),
                        productRequest.getMultipartFile().getOriginalFilename())){
                    product.setProductImage(productImageEntity);
                    flag = true;
                    break;
                }
            }

            if (!flag){
                ProductImageEntity newProductImage = new ProductImageEntity();
                newProductImage.setImgName(fileName);
                productImageRepository.save(newProductImage);
                product.setProductImage(newProductImage);
            }
        }else throw new RuntimeException("Multipath file is null.");

        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(int id) {
        if (productRepository.existsById(id)){
            productRepository.deleteById(id);
        }else {
            throw new RuntimeException("Product not found with id:" + id);
        }
    }


}
