package io.biza.deepthought.shared.persistence.model.customer.bank;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.shared.persistence.model.bank.transaction.BankAccountTransactionData;
import io.biza.deepthought.shared.persistence.model.customer.CustomerData;
import io.biza.deepthought.shared.persistence.model.grant.GrantCustomerAccountData;
import io.biza.deepthought.shared.persistence.model.grant.GrantData;
import io.biza.deepthought.shared.persistence.model.product.ProductBundleData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Valid
@Table(name = "CUSTOMER_ACCOUNT",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"CUSTOMER_ID", "BANK_ACCOUNT_ID"})})
public class CustomerAccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @OneToMany(mappedBy = "customerAccount", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<GrantCustomerAccountData> grants;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID", nullable = false,
      foreignKey = @ForeignKey(name = "CUSTOMER_ACCOUNT_CUSTOMER_ID_FK"))
  @ToString.Exclude
  CustomerData customer;

  @ManyToOne
  @JoinColumn(name = "BANK_ACCOUNT_ID", nullable = false,
      foreignKey = @ForeignKey(name = "CUSTOMER_ACCOUNT_BANK_ACCOUNT_ID_FK"))
  BankAccountData bankAccount;

  @Column(name = "OWNER")
  @Type(type = "true_false")
  Boolean owner;

  @PrePersist
  public void prePersist() {
    if (this.customer() != null) {
      Set<CustomerAccountData> set = new HashSet<CustomerAccountData>();
      if (this.customer().accounts() != null) {
        set.addAll(this.customer().accounts());
      }
      set.add(this);
      this.customer().accounts(set);
    }

    if (this.bankAccount() != null) {
      Set<CustomerAccountData> set = new HashSet<CustomerAccountData>();
      if (this.bankAccount().customerAccounts() != null) {
        set.addAll(this.bankAccount().customerAccounts());
      }
      set.add(this);
      this.bankAccount().customerAccounts(set);
    }
    
    if(this.grants() != null) {
      for(GrantCustomerAccountData grant : this.grants()) {
        grant.customerAccount(this);
      }
    }
  }

}
