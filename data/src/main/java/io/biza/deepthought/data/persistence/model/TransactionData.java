package io.biza.deepthought.data.persistence.model;

import java.math.BigDecimal;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Currency;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingProductDiscountType;
import io.biza.babelfish.cdr.enumerations.BankingTransactionStatus;
import io.biza.babelfish.cdr.enumerations.BankingTransactionType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingAdditionalInformationData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
@Table(name = "TRANSACTION")
public class TransactionData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  @NotNull
  private AccountData account;
  
  @Column(name = "TRANSACTION_TYPE")
  @Enumerated(EnumType.STRING)
  @NotNull
  private BankingTransactionType type;
  
  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  @NotNull
  private BankingTransactionStatus status;
  
  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "ORIGINATED")
  private OffsetDateTime originated;

  @Column(name = "POSTED")
  private OffsetDateTime posted;
  
  @Column(name = "APPLIED")
  private OffsetDateTime applied;
  
  @Column(name = "AMOUNT")
  private BigDecimal amount;
  
  @Column(name = "CURRENCY")
  @Builder.Default
  private Currency currency = Currency.getInstance("AUD");
  
  @Column(name = "REFERENCE")
  @NotNull
  @Builder.Default
  private String reference = "";
  
  @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, optional = true)
  private TransactionCardData card;
  
  @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, optional = true)
  private TransactionBPAYData bpay;
  
  @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, optional = true)
  private TransactionAPCSData apcs;


}
