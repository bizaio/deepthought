package io.biza.deepthought.shared.persistence.specification;

import java.util.Arrays;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData_;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitData_;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitData;

public class DirectDebitSpecifications {

  public static Specification<DirectDebitData> accountId(UUID accountId) {
    return (root, query, cb) -> {
      Join<DirectDebitData, BankAccountData> grantJoin = root.join(DirectDebitData_.account);
      return cb.equal(grantJoin.get(BankAccountData_.id), accountId);
    };
  }
  
  public static Specification<DirectDebitData> accountIds(UUID... accountIds) {
    return (root, query, cb) -> {
      Join<DirectDebitData, BankAccountData> grantJoin = root.join(DirectDebitData_.account);
      return grantJoin.get(BankAccountData_.id).in(Arrays.asList(accountIds));
    };
  }
  
}
