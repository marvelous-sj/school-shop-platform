package xyz.marsj.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.marsj.o2o.dto.ProductCategoryExecution;
import xyz.marsj.o2o.entity.ProductCategory;
import xyz.marsj.o2o.enums.ProductCategoryStateEnum;
import xyz.marsj.o2o.exceptions.ProductCategoryOperationException;
import xyz.marsj.o2o.mapper.ProductCategoryMapper;
import xyz.marsj.o2o.mapper.ProductMapper;
import xyz.marsj.o2o.service.IProductCategoryService;
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	@Autowired
	private ProductMapper productMapper;
	@Override
	public List<ProductCategory> queryProductCategoryList(long shopId) {
		return  productCategoryMapper.queryProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) {
			if(productCategoryList!=null&&productCategoryList.size()>0){
				try {
					int effectedNum = productCategoryMapper.batchInsertProductCategory(productCategoryList);
					if(effectedNum<=0){
						throw new ProductCategoryOperationException("商品类别创建失败");
					}else{
						return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS); 
					}
				} catch (Exception e) {
					throw new ProductCategoryOperationException("batchInsertProductCategory error"+e.getMessage());
				}
			}else{
				return new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR);
			}
	}

	@Override
	@Transactional
	public ProductCategoryExecution delectProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		try {
			int effected = productMapper.updateProductCategoryToNULL(productCategoryId);
			if(effected<0){
				throw new ProductCategoryOperationException("更新商品类别失败");
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error"+e.getMessage());
		}
		int effectedNum = productCategoryMapper.delectProductCategory(productCategoryId, shopId);
		try {
			if(effectedNum<=0){
				throw new ProductCategoryOperationException("删除商品类别失败");
			}else{
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error"+e.getMessage());
		}
		
	}


}
