-- Tạo database
CREATE DATABASE IF NOT EXISTS demoproduct;

-- Sử dụng database
USE demoproduct;

-- Tạo bảng products
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    image VARCHAR(200),
    category VARCHAR(50) NOT NULL
);

-- Thêm dữ liệu mẫu
INSERT INTO products (name, price, image, category) VALUES 
    ('iPhone 15 Pro Max', 29990000, NULL, 'DIEN_THOAI'),
    ('Samsung Galaxy S24', 21990000, NULL, 'DIEN_THOAI'),
    ('MacBook Pro M3', 45990000, NULL, 'LAPTOP'),
    ('Dell XPS 15', 35990000, NULL, 'LAPTOP');
