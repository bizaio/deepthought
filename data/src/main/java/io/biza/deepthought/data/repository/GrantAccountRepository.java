package io.biza.deepthought.data.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.payments.PayeeData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.product.ProductData;

@Repository
public interface GrantAccountRepository extends JpaRepository<GrantAccountData, UUID>, JpaSpecificationExecutor<GrantAccountData> {
  
  
}
