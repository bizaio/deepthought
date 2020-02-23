package io.biza.deepthought.shared.persistence.model.bank.payments;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioSchemeType;
import io.biza.deepthought.shared.persistence.model.bank.account.BankAccountData;
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
@Table(name = "DIRECT_DEBIT")
public class DirectDebitData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  DioSchemeType schemeType = DioSchemeType.DIO_BANKING;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false, foreignKey = @ForeignKey(name = "DIRECT_DEBIT_ACCOUNT_ID_FK"))
  @ToString.Exclude
  @NotNull
  BankAccountData account;
  
  @OneToOne(mappedBy = "directDebit", cascade = CascadeType.ALL, optional = false)
  DirectDebitAuthorisedEntityData authorisedEntity;
  
  @Column(name = "LAST_DEBIT_DATETIME")
  OffsetDateTime lastDebitDateTime;
  
  @Column(name = "LAST_DEBIT_AMOUNT")
  BigDecimal lastDebitAmount;
  
  @PrePersist
  public void prePersist() {
    if (this.authorisedEntity() != null) {
      this.authorisedEntity().directDebit(this);
    }
  }
  
}
