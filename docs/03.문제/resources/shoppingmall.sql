/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2023. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */
use nhn_academy_32;

CREATE TABLE `Users` (
                         `user_id` varchar(50) NOT NULL COMMENT '아이디',
                         `user_name` varchar(50) NOT NULL COMMENT '이름',
                         `user_password` varchar(200) NOT NULL COMMENT 'mysql password 사용',
                         `user_birth` varchar(8) NOT NULL COMMENT '생년월일 : 19840503',
                         `user_auth` varchar(10) NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
                         `user_point` int NOT NULL COMMENT 'default : 1000000',
                         `created_at` datetime NOT NULL COMMENT '가입일자',
                         `lastest_login_at` datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';

CREATE TABLE Categories (
                            category_id		INT	auto_increment NOT NULL ,
                            category_name	varchar(50) NOT NULL ,

                            CONSTRAINT pk_Categories PRIMARY KEY(category_id)
);
CREATE TABLE Products (
                          product_id	INT	not null auto_increment,
                          user_id varchar(50) not null ,
                          category_id	INT not null ,
                          product_name	nvarchar(120) not null , -- 상품 이름
                          product_number	INT not null , -- 상품 수량
                          product_image	nvarchar(255) not null ,
                          product_unit_cost	decimal(15),
                          product_description	text(100),

                          PRIMARY KEY (`product_id`),
                          CONSTRAINT fk_Products_Users FOREIGN KEY(user_id) REFERENCES Users(user_id),
                          CONSTRAINT fk_Products_Categories FOREIGN KEY(category_id) REFERENCES Categories(category_id)
);
drop table Products;
desc Products;
CREATE TABLE Customers (
                           CustomerID	int auto_increment,
                           Name	varchar(10),
                           EmailAddress varchar(100)	UNIQUE,
                           Password	varchar(12),

                           CONSTRAINT pk_Customer PRIMARY KEY(CustomerID)
);

CREATE TABLE Reviews (
                         ReviewID	int auto_increment,
                         ProductID	int,
                         CustomerID	int,
                         Rating		int,
                         Comments	text,

                         CONSTRAINT pk_ReviewID PRIMARY KEY(ReviewID),
                         CONSTRAINT fk_Review_Products FOREIGN KEY(ProductID) REFERENCES Products(product_id),
                         CONSTRAINT fk_Review_Customer FOREIGN KEY(CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Orders (
                        OrderID		int auto_increment,
                        CustomerID	int,
                        OrderDate	Datetime,
                        ShipDate	Datetime,

                        CONSTRAINT pk_Orders PRIMARY KEY(OrderID),
                        CONSTRAINT fk_Orders_CustomerID FOREIGN KEY(CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE OrderDetails (
                              OrderID	int,
                              ProductID	int,
                              Quantity	int,
                              UnitCost	decimal(15),

                              CONSTRAINT pk_OrderDetails PRIMARY KEY(OrderID, ProductID),
                              CONSTRAINT fk_OrderDetails_Orders FOREIGN KEY(OrderID) REFERENCES Orders(OrderID),
                              CONSTRAINT fk_OrderDetails_Products FOREIGN KEY(ProductID) REFERENCES Products(ProductID)
);

-- 장바구니 테이블
CREATE TABLE IF NOT EXISTS Cart (
                                    cart_id INT AUTO_INCREMENT PRIMARY KEY,
                                    user_id VARCHAR(50) NOT NULL,
                                    product_id INT NOT NULL,
                                    quantity INT NOT NULL DEFAULT 1,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
                                    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE,
                                    UNIQUE KEY unique_user_product (user_id, product_id)
);
drop table Cart;
drop table Products;
drop table OrderItems;

-- 주문 테이블
CREATE TABLE IF NOT EXISTS Orders (
                                      order_id INT AUTO_INCREMENT PRIMARY KEY,
                                      user_id VARCHAR(50) NOT NULL,
                                      total_amount INT NOT NULL,
                                      ordered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- 주문 상품 테이블
CREATE TABLE IF NOT EXISTS OrderItems (
                                          order_item_id INT AUTO_INCREMENT PRIMARY KEY,
                                          order_id INT NOT NULL,
                                          product_id INT NOT NULL,
                                          product_name VARCHAR(200) NOT NULL,
                                          quantity INT NOT NULL,
                                          unit_price INT NOT NULL,
                                          FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
                                          FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
);

-- 인덱스 생성
CREATE INDEX idx_cart_user_id ON Cart(user_id);
CREATE INDEX idx_orders_user_id ON Orders(user_id);
CREATE INDEX idx_orders_ordered_at ON Orders(ordered_at DESC);
CREATE INDEX idx_order_items_order_id ON OrderItems(order_id);


CREATE TABLE ShoppingCart (
                              RecordID	int	auto_increment,
                              CartID		nvarchar(150),
                              Quantity	int,
                              ProductID	int,
                              DateCreateed	Datetime DEFAULT NOW(),

                              CONSTRAINT pk_RecordID PRIMARY KEY(RecordID),
                              CONSTRAINT fk_cart_ProductID FOREIGN KEY(ProductID) REFERENCES Products(ProductID)
);


