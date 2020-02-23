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
import io.biza.deepthought.admin.api.delegate.ProductRateDepositAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.admin.support.DeepThoughtValidator;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.dio.product.DioProductRateDeposit;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.persistence.model.product.banking.BankProductRateDepositData;
import io.biza.deepthought.data.persistence.model.product.banking.BankProductRateDepositTierData;
import io.biza.deepthought.data.repository.ProductBankingRateDepositRepository;
import io.biza.deepthought.data.repository.ProductBankingRateDepositTierRepository;
import io.biza.deepthought.data.repository.ProductRepository;
import io.biza.deepthought.shared.controller.DeepThoughtMapper;
import lombok.extern.slf4j.Slf4j;

@Controller
@Validated
@Slf4j
public class ProductRateDepositAdminApiDelegateImpl implements ProductRateDepositAdminApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  ProductBankingRateDepositRepository rateRepository;

  @Autowired
  ProductBankingRateDepositTierRepository tierRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  private Validator validator;

  @Override
  public ResponseEntity<List<DioProductRateDeposit>> listProductRateDeposits(UUID brandId, UUID productId) {

    List<BankProductRateDepositData> feeList =
        rateRepository.findAllByProduct_Product_Brand_IdAndProduct_Product_Id(brandId, productId);
    LOG.debug("Listing fees and have database result of {}", feeList);
    return ResponseEntity.ok(mapper.mapAsList(feeList, DioProductRateDeposit.class));
  }

  @Override
  public ResponseEntity<DioProductRateDeposit> getProductRateDeposit(UUID brandId, UUID productId, UUID id) {
    Optional<BankProductRateDepositData> data = rateRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (data.isPresent()) {
      LOG.debug("Get Product Deposit Rate for brand {} and product {} returning: {}", brandId, productId,
          data.get());
      return ResponseEntity.ok(mapper.map(data.get(), DioProductRateDeposit.class));
    } else {
      LOG.warn("Get product deposit rate for brand {} and product {} not found", brandId, productId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioProductRateDeposit> createProductRateDeposit(UUID brandId, UUID productId,
      DioProductRateDeposit createData) throws ValidationListException {

    DeepThoughtValidator.validate(validator, createData);

    Optional<ProductData> product = productRepository.findByIdAndBrandId(productId, brandId);

    if (!product.isPresent()) {
      throw ValidationListException.builder().type(DioExceptionType.INVALID_BRAND_AND_PRODUCT)
          .explanation(Labels.ERROR_INVALID_BRAND_AND_PRODUCT).build();
    }

    BankProductRateDepositData data = mapper.map(createData, BankProductRateDepositData.class);

    if (data.tiers() != null) {
      for(BankProductRateDepositTierData tier : data.tiers()) {
        if (tier.applicabilityConditions() != null) {
          tier.applicabilityConditions().rateTier(tier);
        }

        tier.depositRate(data);
      }
    }

    LOG.debug("Attempting to save: {}", data);

    if (!product.get().schemeType().equals(createData.schemeType())) {
      throw ValidationListException.builder().type(DioExceptionType.UNSUPPORTED_PRODUCT_SCHEME_TYPE)
          .explanation(Labels.ERROR_UNSUPPORTED_PRODUCT_SCHEME_TYPE).build();

    }

    if (product.get().schemeType().equals(DioSchemeType.CDR_BANKING)) {
      data.product(product.get().cdrBanking());
    }

    data = rateRepository.save(data);
    LOG.debug("Create Product Deposit Rate for brand {} and product {} returning: {}", brandId, productId,
        data);
    return getProductRateDeposit(brandId, productId, data.id());
  }

  @Override
  public ResponseEntity<Void> deleteProductRateDeposit(UUID brandId, UUID productId, UUID id) {
    Optional<BankProductRateDepositData> optionalData = rateRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (optionalData.isPresent()) {
      LOG.debug("Deleting product deposit rate with brand: {} productId: {} id: {}", brandId, productId, id);
      rateRepository.delete(optionalData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioProductRateDeposit> updateProductRateDeposit(UUID brandId, UUID productId, UUID id,
      DioProductRateDeposit updateData) throws ValidationListException {

    DeepThoughtValidator.validate(validator, updateData);

    Optional<BankProductRateDepositData> optionalData = rateRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (optionalData.isPresent()) {
      BankProductRateDepositData data = optionalData.get();
      
      if (data.tiers() != null) {
        for(BankProductRateDepositTierData tier : data.tiers()) {
          data.tiers().remove(tier);
          tierRepository.deleteById(tier.id());
        }
      }

      data = rateRepository.save(data);

      LOG.debug("DepositRate data is now: {}", data);
      
      mapper.map(updateData, data);
      
      if (data.tiers() != null) {
        for(BankProductRateDepositTierData tier : data.tiers()) {
          if (tier.applicabilityConditions() != null) {
            tier.applicabilityConditions().rateTier(tier);
          }
          tier.depositRate(data);
        }
      }
      
      data = rateRepository.save(data);

      LOG.debug("Updated product deposit rate for brand: {} productId: {} id: {} with data of {}", brandId,
          productId, id, data);
      return getProductRateDeposit(brandId, productId, data.id());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
