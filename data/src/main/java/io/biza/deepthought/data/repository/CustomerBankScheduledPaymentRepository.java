package io.biza.deepthought.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import io.biza.deepthought.data.persistence.model.bank.payments.BankAccountDirectDebitData;
import io.biza.deepthought.data.persistence.model.bank.payments.CustomerBankScheduledPaymentData;
import io.biza.deepthought.data.persistence.model.grant.GrantResourceData;

@Repository
public interface CustomerBankScheduledPaymentRepository extends JpaRepository<CustomerBankScheduledPaymentData, UUID>, JpaSpecificationExecutor<CustomerBankScheduledPaymentData> {

}
