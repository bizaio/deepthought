package io.biza.deepthought.data.persistence.model.person;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.deepthought.data.enumerations.DioSchemeType;
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
@Table(name = "PERSON_ADDRESS")
@EqualsAndHashCode
public class PersonAddressData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_COMMON;
  
  @ManyToOne
  @JoinColumn(name = "PERSON_ID", nullable = false)
  @ToString.Exclude
  PersonData person;
  
  @Column(name = "PURPOSE")
  @Enumerated(EnumType.STRING)
  AddressPurpose purpose;
  
  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, optional = true)
  PersonAddressSimpleData simple;
  
  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, optional = true)
  PersonAddressPAFData paf;
  
}
