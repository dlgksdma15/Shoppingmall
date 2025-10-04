package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.product.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    /**
     * 상품을 저장합니다.
     * @param product 저장할 상품
     * @return 저장된 상품의 ID
     */
    int save(Product product);

    /**
     * 상품 정보를 수정합니다.
     * @param product 수정할 상품 정보
     * @return 수정된 행의 수
     */
    int update(Product product);

    /**
     * 상품 ID로 상품을 삭제합니다.
     * @param productId 삭제할 상품 ID
     * @return 삭제된 행의 수
     */
    int deleteByProductId(long productId);

    /**
     * 상품 ID로 특정 상품을 조회합니다.
     * @param productId 조회할 상품 ID
     * @return Optional<Product> 객체 (상품이 없으면 Optional.empty())
     */
    Optional<Product> findById(long productId);

    /**
     * 모든 상품 목록을 조회합니다.
     * @return 상품 리스트
     */
    List<Product> findAll();

    /**
     * 최근 등록된 상품을 페이지 단위로 조회합니다.
     * @param offset 페이지 시작 위치
     * @param limit 페이지당 아이템 수
     * @return 상품 리스트
     */
    List<Product> findLatestProducts(int offset, int limit);

    /**
     * 전체 상품의 개수를 조회합니다.
     * @return 전체 상품 개수
     */
    long totalCount();
}