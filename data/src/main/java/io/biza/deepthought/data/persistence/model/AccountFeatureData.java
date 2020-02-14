package io.biza.deepthought.data.persistence.model;

import java.net.URI;
import java.util.Set;
import java.util.HashSet;
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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.biza.babelfish.cdr.enumerations.BankingProductFeatureType;
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeatureData;
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
@Table(name = "ACCOUNT_FEATURE")
public class AccountFeatureData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @JsonIgnore
  @ToString.Exclude
  private AccountData account;

  @Column(name = "FEATURE_TYPE")
  @Enumerated(EnumType.STRING)
  private BankingProductFeatureType featureType;

  @Column(name = "ADDITIONAL_VALUE", length = 4096)
  private String additionalValue;

  @Column(name = "ADDITIONAL_INFO")
  @Lob
  private String additionalInfo;

  @Column(name = "ADDITIONAL_INFO_URI")
  @Convert(converter = URIDataConverter.class)
  private URI additionalInfoUri;
  
  @Column(name = "IS_ACTIVATED", nullable = false)
  @NotNull
  @NonNull
  @Type(type = "true_false")
  @Builder.Default
  Boolean isActivated = true;
  
}
