package io.biza.deepthought.product.service;

import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.biza.deepthought.product.api.requests.RequestListProducts;
import io.biza.deepthought.shared.persistence.model.product.ProductData;
import io.biza.deepthought.shared.persistence.repository.ProductRepository;
import io.biza.deepthought.shared.persistence.specification.ProductBankingSpecifications;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  
  public Optional<ProductData> getProduct(UUID productId) {
    LOG.debug("Retrieving product with identifier of {}", productId);
    Optional<ProductData> productResult = productRepository.findById(productId);
    return productResult;
  }
  
  public Page<ProductData> listProducts(
      RequestListProducts requestList) {
    LOG.debug("Retrieving a list of products with input request of {}", requestList);

    Specification<ProductData> filterSpecifications = Specification.where(null);

    if (requestList.effective() != null) {
      filterSpecifications =
          filterSpecifications.and(ProductBankingSpecifications.effective(requestList.effective()));
    }

    if (requestList.updatedSince() != null) {
      filterSpecifications =
          filterSpecifications.and(ProductBankingSpecifications.updatedSince(requestList.updatedSince()));
    }

    if (requestList.productCategory() != null) {
      filterSpecifications = filterSpecifications
          .and(ProductBankingSpecifications.productCategory(requestList.productCategory()));
    }

    if (StringUtils.isNotBlank(requestList.brand())) {
      filterSpecifications =
          filterSpecifications.and(ProductBankingSpecifications.brand(requestList.brand()));
    }
    
    /**
     * Paginated Result
     */
    return productRepository.findAll(filterSpecifications,
        PageRequest.of(requestList.page() - 1, requestList.pageSize()));
  }
}
