package com.nhnacademy.shoppingmall.category.repository;

import com.nhnacademy.shoppingmall.category.domain.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    /**
     * 카테고리를 저장합니다.
     * @param category 저장할 카테고리
     * @return 저장된 행의 수
     */
    int save(Category category);

    /**
     * 카테고리 정보를 수정합니다.
     * @param category 수정할 카테고리 정보
     * @return 수정된 행의 수
     */
    int update(Category category);

    /**
     * 카테고리 ID로 카테고리를 삭제합니다.
     * @param categoryId 삭제할 카테고리 ID
     * @return 삭제된 행의 수
     */
    int deleteByCategoryId(int categoryId);

    /**
     * 카테고리 ID로 특정 카테고리를 조회합니다.
     * @param categoryId 조회할 카테고리 ID
     * @return Optional<Category> 객체
     */
    Optional<Category> findById(int categoryId);

    /**
     * 모든 카테고리 목록을 조회합니다.
     * @return 카테고리 리스트
     */
    List<Category> findAll();
}