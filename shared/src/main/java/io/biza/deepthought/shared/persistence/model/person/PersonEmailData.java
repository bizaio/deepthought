package io.biza.deepthought.shared.persistence.model.person;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioEmailType;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioSchemeType;
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
@Table(name = "PERSON_EMAIL")
public class PersonEmailData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_COMMON;
  
  @ManyToOne
  @JoinColumn(name = "PERSON_ID", nullable = false, foreignKey = @ForeignKey(name = "PERSON_EMAIL_PERSON_ID_FK"))
  @ToString.Exclude
  PersonData person;
  
  @Column(name = "IS_PREFERRED", nullable = false)
  @NotNull
  @NonNull
  @Type(type = "true_false")
  @Builder.Default
  Boolean isPreferred = false;
  
  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  DioEmailType type;
  
  @Column(name = "ADDRESS")
  @NotNull
  @Email
  String address;

}
