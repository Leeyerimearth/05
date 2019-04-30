package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml"})
public class PurchaseServiceTest{

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void testAddPurchase() throws Exception
	{
		Purchase purchase = new Purchase();
		User user = new User();
		user.setUserId("user17");
		purchase.setBuyer(user);
		purchase.setDivyAddr("안양시 동안구");
		purchase.setDivyDate("20201212");
		purchase.setDivyRequest("넣어지나 봐주세요!");
		//purchase.setOrderDate(orderDate);
		purchase.setPaymentOption("0");
		Product product = productService.getProduct(10000);
		System.out.println(product.getProdNo());
		purchase.setPurchaseProd(product);
		//등등 .. db에서 확인해바방.
		
		purchaseService.addPurchase(purchase);
		
		//purchase =purchaseService.getPurchase2(10000);
		
		Assert.assertEquals("user17",purchase.getBuyer().getUserId());
		Assert.assertEquals("안양시 동안구", purchase.getDivyAddr());
		Assert.assertEquals("20201212", purchase.getDivyDate());
		Assert.assertEquals("넣어지나 봐주세요!",purchase.getDivyRequest());
	}
	
	//@Test
	public void testGetPurchase() throws Exception
	{
		
		/*Purchase purchase = new Purchase();
		User user = new User();
		user.setUserId("testUserId");
		purchase.setBuyer(user);
		purchase.setDivyAddr("안양시 동안구");
		purchase.setDivyDate("20201212");
		purchase.setDivyRequest("넣어지나 봐주세요!");
		//purchase.setOrderDate(orderDate);
		purchase.setPaymentOption("0");
		Product product = productService.getProduct(10000);
		System.out.println(product.getProdNo());
		purchase.setPurchaseProd(product);
		//등등 .. db에서 확인해바방.
		*/
		
		Purchase purchase = purchaseService.getPurchase(10022);
		System.out.println(purchase);
		Assert.assertEquals("user17", purchase.getBuyer().getUserId());
		Assert.assertEquals(10000,purchase.getPurchaseProd().getProdNo());
	}
	
	//@Test
	public void testGetPurchase2()
	{
		//Search search = new Search();
		//search.setCurrentPage(1);
		//search.setPageSize(3);
		//purchaseService.getPurchase2(prodNo);
		
		//getPurchase2가 prodNo로 purchase를 가져오는건데, prodNo는 unique하지 않다.
		//Purchase purchase = purchaseService.getPurchase2(prodNo);
	}
	
	//@Test
	public void testGetPurchaseList() throws Exception
	{
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		
		String buyerId = "user17";
		Map<String,Object> map =purchaseService.getPurchaseList(search, buyerId);
		List<Object> list = (List<Object>) map.get("list");
		
		Assert.assertEquals(3, list.size());
		
		int totalCount = (int) map.get("totalCount");
		System.out.println(totalCount);
		
	}
	
	//@Test
	public void testGetSaleList() throws Exception
	{
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		
		Map<String,Object> map = purchaseService.getSaleList(search);
		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());
		
		int totalCount = (int) map.get("totalCount");
		System.out.println(totalCount);
		
	}
	
	//@Test
	public void testUpdatePurcahse() throws Exception
	{
		
		Purchase purchase = purchaseService.getPurchase(10022);
		purchase.setReceiverName("이예림");
		purchase.setDivyRequest("바꿧다test");
		purchase.setPaymentOption("1");
		
		purchaseService.updatePurcahse(purchase);
		
		purchase=purchaseService.getPurchase(10022);
		
		Assert.assertEquals("이예림", purchase.getReceiverName());
		Assert.assertEquals("바꿧다test", purchase.getDivyRequest());
		//2개만 테스트 해봤다.
	}
	
	@Test
	public void testUpdateTranCode() throws Exception
	{
		Purchase purchase = purchaseService.getPurchase(10022);
		
		purchaseService.updateTranCode(purchase);
		
		purchase = purchaseService.getPurchase(10022);
		
		Assert.assertEquals("002",purchase.getTranCode());
		
	}
}
