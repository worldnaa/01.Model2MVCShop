package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService {
	//Field
	private ProductDAO productDAO;
	
	//Constructor
	public ProductServiceImpl() {
		productDAO = new ProductDAO();
	}

	//Method
	public void addProduct(ProductVO productVO) throws Exception {
		System.out.println("<<<여기는 Impl : addProduct() 실행>>>");
		productDAO.insertProduct(productVO);
	}

	
	public ProductVO getProduct(int prodNo) throws Exception {
		System.out.println("<<<여기는 Impl : getProduct() 실행>>>");
		return productDAO.findProduct(prodNo);
	}

	
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		System.out.println("<<<여기는 Impl : getProductList() 실행>>>");
		return productDAO.getProductList(searchVO);
	}

	
	public void updateProduct(ProductVO productVO) throws Exception {
		System.out.println("<<<여기는 Impl : updateProduct() 실행>>>");
		productDAO.updateProduct(productVO);
	}

}//end of class
