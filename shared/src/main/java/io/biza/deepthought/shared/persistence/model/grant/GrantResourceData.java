package io.biza.deepthought.shared.persistence.model.grant;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
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
@Table(name = "GRANT_RESOURCE",
uniqueConstraints = {@UniqueConstraint(columnNames = {"GRANT_ID", "OBJECT_ID"})})
public class GrantResourceData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "GRANT_ID", nullable = false, foreignKey = @ForeignKey(name = "GRANT_RESOURCE_GRANT_ID_FK"))
  @NotNull
  @ToString.Exclude
  GrantData grant;
  
  @Column(name = "OBJECT_ID", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  @NotNull
  UUID objectId;
}
