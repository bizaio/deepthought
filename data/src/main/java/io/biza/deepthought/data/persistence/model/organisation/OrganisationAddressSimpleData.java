package io.biza.deepthought.data.persistence.model.organisation;

import java.util.Locale;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@Table(name = "CUSTOMER_ORGANISATION_ADDRESS_SIMPLE")
@EqualsAndHashCode
public class OrganisationAddressSimpleData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "ORGANISATION_ADDRESS_ID")
  @ToString.Exclude
  OrganisationAddressData address;
  
  @Column(name = "NAME")
  String name;
  
  @Column(name = "ADDRESS_LINE1")
  @NotNull
  String addressLine1;
  
  @Column(name = "ADDRESS_LINE2")
  String addressLine2;

  @Column(name = "ADDRESS_LINE3")
  String addressLine3;
  
  @Column(name = "POSTCODE")
  String postcode;
  
  @Column(name = "STATE")
  String state;
  
  @Column(name = "COUNTRY")
  @Builder.Default
  Locale country = new Locale("en", "AU");

}
