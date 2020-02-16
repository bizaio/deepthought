package io.biza.deepthought.data.persistence.model.product;

import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.product.ProductBankingData;
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
@Table(name = "PRODUCT")
public class ProductData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "BRAND_ID", nullable = false)
  @ToString.Exclude
  BrandData brand;
  
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<BankAccountData> accounts;

  @Column(name = "NAME", length = 255, nullable = false)
  @NotNull
  @NonNull
  String name;

  @Column(name = "DESCRIPTION", nullable = false)
  @Lob
  @NotNull
  @NonNull
  String description;

  @ManyToMany(mappedBy = "products")
  @ToString.Exclude
  Set<ProductBundleData> bundle;

  @Column(name = "SCHEME_TYPE")
  @Enumerated(EnumType.STRING)
  @NotNull
  @NonNull
  DioSchemeType schemeType;

  @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, optional = true)
  ProductBankingData cdrBanking;

  @AssertTrue(message = "Payload data must be populated based on specified scheme type")
  private boolean isSchemeValuePopulated() {
    if (schemeType.equals(DioSchemeType.CDR_BANKING)) {
      return cdrBanking() != null;
    }
    return false;
  }

  @PrePersist
  public void prePersist() {
    if (this.cdrBanking() != null) {
      this.cdrBanking().product(this);
      
      if(this.cdrBanking().additionalInformation() != null) {
        this.cdrBanking().additionalInformation().product(this.cdrBanking());
      }
    }
  }



}
