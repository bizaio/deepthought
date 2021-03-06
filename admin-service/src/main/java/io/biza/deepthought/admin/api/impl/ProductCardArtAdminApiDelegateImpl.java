package io.biza.deepthought.admin.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.ProductCardArtAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.admin.support.DeepThoughtValidator;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.DioProductCardArt;
import io.biza.deepthought.data.persistence.model.ProductData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingCardArtData;
import io.biza.deepthought.data.repository.ProductCardArtRepository;
import io.biza.deepthought.data.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Controller
@Validated
@Slf4j
public class ProductCardArtAdminApiDelegateImpl implements ProductCardArtAdminApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  ProductCardArtRepository cardArtRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  private Validator validator;
  
  @Override
  public ResponseEntity<List<DioProductCardArt>> listProductCardArts(UUID brandId, UUID productId) {

    List<ProductCdrBankingCardArtData> cardArtList =
        cardArtRepository.findAllByProduct_Product_Brand_IdAndProduct_Product_Id(brandId, productId);
    LOG.debug("Listing cardArts and have database result of {}", cardArtList);
    return ResponseEntity.ok(mapper.mapAsList(cardArtList, DioProductCardArt.class));
  }

  @Override
  public ResponseEntity<DioProductCardArt> getProductCardArt(UUID brandId, UUID productId, UUID id) {
    Optional<ProductCdrBankingCardArtData> data = cardArtRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (data.isPresent()) {
      LOG.debug("Get Product CardArt for brand {} and product {} returning: {}", brandId, productId,
          data.get());
      return ResponseEntity.ok(mapper.map(data.get(), DioProductCardArt.class));
    } else {
      LOG.warn("Get product cardArt for brand {} and product {} not found", brandId, productId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioProductCardArt> createProductCardArt(UUID brandId, UUID productId,
      DioProductCardArt createData) throws ValidationListException {

    DeepThoughtValidator.validate(validator, createData);

    Optional<ProductData> product = productRepository.findByIdAndBrandId(productId, brandId);

    if (!product.isPresent()) {
      throw ValidationListException.builder().type(DioExceptionType.INVALID_BRAND_AND_PRODUCT)
          .explanation(Labels.ERROR_INVALID_BRAND_AND_PRODUCT).build();
    }

    ProductCdrBankingCardArtData data = mapper.map(createData, ProductCdrBankingCardArtData.class);

    LOG.debug("Attempting to save: {}", data);

    if (!product.get().schemeType().equals(createData.schemeType())) {
      throw ValidationListException.builder().type(DioExceptionType.UNSUPPORTED_PRODUCT_SCHEME_TYPE)
          .explanation(Labels.ERROR_UNSUPPORTED_PRODUCT_SCHEME_TYPE).build();

    }

    if (product.get().schemeType().equals(DioSchemeType.CDR_BANKING)) {
      data.product(product.get().cdrBanking());
    }

    data = cardArtRepository.save(data);
    LOG.debug("Create Product CardArt for brand {} and product {} returning: {}", brandId, productId,
        data);
    return getProductCardArt(brandId, productId, data.id());
  }

  @Override
  public ResponseEntity<Void> deleteProductCardArt(UUID brandId, UUID productId, UUID id) {
    Optional<ProductCdrBankingCardArtData> optionalData = cardArtRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (optionalData.isPresent()) {
      LOG.debug("Deleting product cardArt with brand: {} productId: {} id: {}", brandId, productId, id);
      cardArtRepository.delete(optionalData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioProductCardArt> updateProductCardArt(UUID brandId, UUID productId, UUID id,
      DioProductCardArt updateData) throws ValidationListException {

    DeepThoughtValidator.validate(validator, updateData);

    Optional<ProductCdrBankingCardArtData> optionalData = cardArtRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (optionalData.isPresent()) {
      ProductCdrBankingCardArtData data = optionalData.get();
      data = cardArtRepository.save(data);
      mapper.map(updateData, data);
      data = cardArtRepository.save(data);

      LOG.debug("Updated product cardArt for brand: {} productId: {} id: {} with data of {}", brandId,
          productId, id, data);
      return getProductCardArt(brandId, productId, data.id());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
