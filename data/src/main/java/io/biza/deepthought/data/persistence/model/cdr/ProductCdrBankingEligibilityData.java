package io.biza.deepthought.data.persistence.model.cdr;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.biza.babelfish.cdr.enumerations.BankingProductEligibilityType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
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
@Table(name = "PRODUCT_CDR_BANKING_ELIGIBILITY")
public class ProductCdrBankingEligibilityData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID", nullable = false)
  @JsonIgnore
  @ToString.Exclude
  private ProductCdrBankingData product;

  @Column(name = "ELIGIBILITY_TYPE")
  @Enumerated(EnumType.STRING)
  private BankingProductEligibilityType eligibilityType;

  @Column(name = "ADDITIONAL_VALUE", length = 4096)
  private String additionalValue;

  @Column(name = "ADDITIONAL_INFO")
  @Lob
  private String additionalInfo;

  @Column(name = "ADDITIONAL_INFO_URI")
  @Convert(converter = URIDataConverter.class)
  private URI additionalInfoUri;
  
  @PrePersist
  public void prePersist() {
    if (this.product() != null) {
      Set<ProductCdrBankingEligibilityData> set = new HashSet<ProductCdrBankingEligibilityData>();
      if (this.product().eligibility() != null) {
        set.addAll(this.product.eligibility());
      }
      set.add(this);
      this.product().eligibility(set);
    }
  }

}
