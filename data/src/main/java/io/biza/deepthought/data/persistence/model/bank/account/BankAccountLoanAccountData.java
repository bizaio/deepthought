package io.biza.deepthought.data.persistence.model.bank.account;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioLoanRepaymentType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.Constants;
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
@Table(name = "BANK_ACCOUNT_LOAN_ACCOUNT")
public class BankAccountLoanAccountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  private BankAccountData account;
  
  @Column(name = "CREATION_DATETIME")
  @NotNull
  @CreationTimestamp
  OffsetDateTime creationDateTime;
  
  @Column(name = "CREATION_AMOUNT")
  @NotNull
  BigDecimal creationAmount;
  
  @Column(name = "CURRENCY")
  @Builder.Default
  Currency currency = Currency.getInstance(Constants.DEFAULT_CURRENCY);
  
  @Column(name = "CREATION_LENGTH")
  Period creationLength;
  
  @Column(name = "REPAYMENT_FREQUENCY")
  Period repaymentFrequency;
  
  @Column(name = "NEXT_REPAYMENT")
  OffsetDateTime lastRepayment;
  
  @Column(name = "NEXT_REPAYMENT_AMOUNT")
  BigDecimal nextRepaymentAmount;
  
  @Column(name = "REDRAW_AVAILABLE")
  BigDecimal redrawAvailable;
  
  @Column(name = "REPAYMENT_TYPE")
  DioLoanRepaymentType repaymentType;
  
  @PrePersist
  public void prePersist() {
    if (this.account() != null) {
      Set<BankAccountLoanAccountData> set = new HashSet<BankAccountLoanAccountData>();
      if (this.account().loanAccounts() != null) {
        set.addAll(this.account().loanAccounts());
      }
      set.add(this);
      this.account().loanAccounts(set);
    }
  }
  
}
