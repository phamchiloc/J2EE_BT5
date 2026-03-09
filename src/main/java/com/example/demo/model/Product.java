package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá sản phẩm phải cho phép nhập từ 1 - 9999999")
    @Max(value = 9999999, message = "Giá sản phẩm phải cho phép nhập từ 1 - 9999999")
    @Column(name = "price")
    private Integer price;

    @Size(max = 200, message = "Tên hình ảnh không quá 200 kí tự")
    @Column(name = "image")
    private String image;

    @NotNull(message = "Loại danh mục không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
}
