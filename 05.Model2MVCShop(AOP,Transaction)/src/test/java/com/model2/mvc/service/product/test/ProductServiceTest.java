package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception
	{
		Product product = new Product();
		product.setFileName("abc.jpg");
		product.setManuDate("2010-12-4");
		product.setPrice(3000);
		product.setProdDetail("test해볼게요");
		product.setProdName("test");
		product.setQuantity(30);
		
		System.out.println(product);
		
		productService.addProduct(product);
	
	}
	
	//@Test
	public void testGetProduct() throws Exception
	{
		Product product = productService.getProduct(10022);
		
		Assert.assertEquals("abc.jpg", product.getFileName());
		Assert.assertEquals("2010124", product.getManuDate());
		Assert.assertEquals(3000, product.getPrice());
		Assert.assertEquals("test해볼게요", product.getProdDetail());
		Assert.assertEquals("test", product.getProdName());
		Assert.assertEquals(30, product.getQuantity());
		
		Assert.assertNotNull(productService.getProduct(10000));
		//Assert.assertNotNull(productService.getProduct(10008));
	}
	
	//@Test
	public void testUpdateProduct() throws Exception
	{
		Product product = productService.getProduct(10022);
		
		product.setFileName("change.jpg");
		product.setManuDate("20191919");
		product.setPrice(1000);
		product.setProdDetail("업데이트됐나요?");
		product.setProdName("비가온다구");
		product.setQuantity(100);
		
		productService.updateProduct(product);
		
		product =productService.getProduct(10022);
		
		Assert.assertEquals("change.jpg", product.getFileName());
		Assert.assertEquals("20191919", product.getManuDate());
		Assert.assertEquals(1000, product.getPrice());
		Assert.assertEquals("업데이트됐나요?", product.getProdDetail());
		Assert.assertEquals("비가온다구", product.getProdName());
		Assert.assertEquals(100, product.getQuantity());
		
		
	}
	
	@Test
	public void testGetProductList2() throws Exception
	{
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
		Map<String,Object> map =productService.getProductList2(search);
		
		List<Object> list = (List<Object>) map.get("list"); // 아예object로 받아버리넹
		Assert.assertEquals(3, list.size());
		
		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println("totalCount: "+totalCount);
		
		System.out.println("=======================================");
		
		search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("자전거");
	 	map = productService.getProductList2(search);	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println("???"+totalCount);
	
	}


}
