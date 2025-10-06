package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.model.product.domain.Product;
import com.nhnacademy.shoppingmall.model.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.model.product.service.ProductService;
import com.nhnacademy.shoppingmall.model.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do"})
public class IndexController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    // 페이지당 상품 개수
    private static final int ITEMS_PER_PAGE = 9; // 예시: 한 페이지에 9개 상품 표시

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 1. 현재 페이지 번호 가져오기 (기본값: 1)
        int page = 1;
        try {
            String pageParam = req.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException ignored) {
            // 페이지 파라미터가 유효하지 않으면 기본값 1 유지
        }

        // 현재 페이지 번호는 1보다 작을 수 없음
        if (page < 1) {
            page = 1;
        }

        // 2. OFFSET 계산
        int offset = (page - 1) * ITEMS_PER_PAGE;
        int limit = ITEMS_PER_PAGE;

        // 3. 페이징 처리된 상품 목록 가져오기
        List<Product> productList = productService.getProductsWithPaging(offset, limit);

        // 4. 전체 상품 개수 및 전체 페이지 수 계산
        long totalProductCount = productService.getTotalProductCount();
        int totalPages = (int) Math.ceil((double) totalProductCount / ITEMS_PER_PAGE);

        // 현재 페이지가 전체 페이지 수를 초과하는 경우, 마지막 페이지로 설정 (URL에서 잘못된 페이지 번호를 요청한 경우)
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
            // 페이지가 변경되었으므로 상품 목록도 다시 가져올 필요가 있다면 여기에 로직 추가
            // 간단하게는 JSP에서 처리하도록 넘어가거나, 아니면 다시 offset 계산 후 getProductsWithPaging 호출
            offset = (page - 1) * ITEMS_PER_PAGE;
            productList = productService.getProductsWithPaging(offset, limit);
        } else if (totalPages == 0) {
            // 상품이 없는 경우, 페이지를 1로 설정
            page = 1;
        }

        // 5. JSP로 필요한 정보 전달
        req.setAttribute("productList", productList);      // 해당 페이지 상품 목록
        req.setAttribute("currentPage", page);            // 현재 페이지 번호
        req.setAttribute("totalPages", totalPages);        // 전체 페이지 수
        req.setAttribute("itemsPerPage", ITEMS_PER_PAGE);  // 페이지당 상품 개수 (선택 사항)

        return "shop/main/index";
    }
}