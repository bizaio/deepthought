package io.biza.deepthought.data.persistence.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.CommonOrganisationType;
import io.biza.deepthought.data.enumerations.DioCustomerType;
import io.biza.deepthought.data.enumerations.DioPersonPrefix;
import io.biza.deepthought.data.enumerations.DioPersonSuffix;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateLendingTierData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@Table(name = "ORGANISATION")
@EqualsAndHashCode
public class OrganisationData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUSTOMER_ID")
  @ToString.Exclude
  CustomerData customer;
  
  @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<OrganisationPersonData> persons;
  
  @Column(name = "BUSINESS_NAME")
  @NotNull
  String businessName;
  
  @Column(name = "LEGAL_NAME")
  String legalName;
  
  @Column(name = "SHORT_NAME")
  String shortName;
  
  @Column(name = "ABN")
  String abn;
  
  @Column(name = "ACN")
  String acn;
  
  @Column(name = "ACNC_REGISTERED")
  @Type(type = "true_false")
  Boolean isACNC;
  
  @Column(name = "ANZSIC_CODE")
  String industryCode;
  
  @Column(name = "ORGANISATION_TYPE")
  @Enumerated(EnumType.STRING)
  CommonOrganisationType organisationType;
  
  @Column(name = "REGISTERED_COUNTRY")
  @Builder.Default
  Locale registeredCountry = new Locale("en", "AU");
  
  @Column(name = "ESTABLISHMENT_DATE")
  LocalDate establishmentDate;
  
  @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
  @ToString.Exclude
  private Set<OrganisationAddressData> physicalAddress;

}
