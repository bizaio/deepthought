package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.product.ProductBankingRateDepositTierData;

@Repository
public interface ProductRateDepositTierRepository
    extends JpaRepository<ProductBankingRateDepositTierData, UUID> {
  public List<ProductBankingRateDepositTierData> findAllByDepositRate_Product_Product_Brand_IdAndDepositRate_Product_Product_IdAndDepositRate_Id(
      UUID brandId, UUID productId, UUID depositRateId);
  public Optional<ProductBankingRateDepositTierData> findByIdAndDepositRate_Product_Product_Brand_IdAndDepositRate_Product_Product_IdAndDepositRate_Id(
      UUID id, UUID brandId, UUID productId, UUID depositRateId);
}


