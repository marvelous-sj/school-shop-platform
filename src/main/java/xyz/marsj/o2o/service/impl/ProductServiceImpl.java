package xyz.marsj.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.ImgHolder;
import xyz.marsj.o2o.dto.ProductExecution;
import xyz.marsj.o2o.entity.Product;
import xyz.marsj.o2o.entity.ProductImg;
import xyz.marsj.o2o.enums.ProductStateEnum;
import xyz.marsj.o2o.exceptions.ProductOperationException;
import xyz.marsj.o2o.mapper.ProductImgMapper;
import xyz.marsj.o2o.mapper.ProductMapper;
import xyz.marsj.o2o.service.IProductService;
import xyz.marsj.o2o.util.ImgUtil;
import xyz.marsj.o2o.util.PageUtil;
import xyz.marsj.o2o.util.PathUtil;
@Service
public class ProductServiceImpl implements IProductService {
		
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductImgMapper productImgMapper;
	@Override
	@Transactional
	/**
	   //1.处理缩略图，获取缩略路径并赋值给product
		//2.往tb_product 写入商品信息，获取productId
		//3.结合productId批量处理商品详情图
		//4.将商品详情图批量插入到tb_product_img中
	 */
	public ProductExecution addProduct(Product product, ImgHolder thumbnail, List<ImgHolder> thumbnailList) {
		//判空
		if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//添加商品信息 1为上架
			product.setEnableStatus(1);
			if(thumbnail!=null){
				addThumbnail(product,thumbnail);
			}
			
			try {
				int effectedNum = productMapper.insertProduct(product);
				if(effectedNum<=0){
					throw new ProductOperationException("添加商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("添加商品失败"+e.getMessage());
			}
			if(thumbnailList!=null&&thumbnailList.size()>0){
				addProductImgList(product,thumbnailList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else{
			return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
		}
		
	}
	
	private void addProductImgList(Product product, List<ImgHolder> thumbnailList) {
		String path=PathUtil.getShopImgPath(product.getShop().getShopId());
		List<ProductImg> productImgList =new ArrayList<ProductImg>();
		for (ImgHolder imgHolder : thumbnailList) {
			String imgAddr = ImgUtil.generateNormalImg(imgHolder, path);
			ProductImg productImg=new ProductImg(); 
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		if(productImgList.size()>0){
			try {
				int effected = productImgMapper.batchInsertProductImg(productImgList);
				if(effected<=0){
					  throw new ProductOperationException("添加商品详情图失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("添加商品详情图失败"+e.getMessage());
			}
		}
		
	}
	private void addThumbnail(Product product, ImgHolder thumbnail) {
		String path=PathUtil.getShopImgPath(product.getShop().getShopId());
		String imgAddr = ImgUtil.generateThumbnail(thumbnail, path);
		product.setImgAddr(imgAddr);
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImgHolder thumbanil, List<ImgHolder> thumbnailList) {
		//判空
		if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
			product.setLastEditTime(new Date());
			//操作缩略图
			if(thumbanil!=null){
				Product tempProduct = productMapper.queryProductById(product.getProductId());
				if(tempProduct.getImgAddr()!=null){
					ImgUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product,thumbanil);
			}
			//操作详情图
			if(thumbnailList!=null&&thumbnailList.size()>0){
				deleteProductImgList(product.getProductId());
				addProductImgList(product,thumbnailList);
			}
			try {
				int effected = productMapper.updateProduct(product);
				if(effected<=0){
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("更新商品信息失败"+e.getMessage());
			}
		}else{
			return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
		}
	}

	private void deleteProductImgList(Long productId) {
//      自己写的方法 后来查了 不行 但还是不太能理解为什么不行
//		Product tempProduct = productMapper.queryProductById(productId);
//		List<ProductImg> productImgList = tempProduct.getProductImgList();
//		if(productImgList.size()>0){
//			for (ProductImg productImg : productImgList) {
//				ImgUtil.deleteFileOrPath(productImg.getImgAddr());
//			}
//			 productImgMapper.deleteProductImgByProductId(productId);
//		}
		List<ProductImg> tempProduct = productImgMapper.queryProductImgList(productId);
		if(tempProduct.size()>0){
			for (ProductImg productImg : tempProduct) {
				ImgUtil.deleteFileOrPath(productImg.getImgAddr());
			}
			 productImgMapper.deleteProductImgByProductId(productId);
		}
	}

	@Override
	public Product queryProductById(long id) {
		return productMapper.queryProductById(id);
	}

	@Override
	public ProductExecution queryProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageUtil.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productMapper.queryProductList(productCondition, rowIndex, pageSize);
		int count = productMapper.queryProductCount(productCondition);
		ProductExecution pe=new ProductExecution();
		if(productList!=null){
			pe.setCount(count);
			pe.setProductList(productList);
		}else{
			pe.setState(ProductStateEnum.INNER_ERROR.getState());
		}
		return pe;
	}

}
