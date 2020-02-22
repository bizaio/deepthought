package io.biza.deepthought.data.persistence.model.product.banking;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
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
@Table(name = "PRODUCT_BANK_CARD_ART")
public class BankProductCardArtData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_BANK_ID", nullable = false, foreignKey = @ForeignKey(name = "PRODUCT_BANK_CARD_ART_PRODUCT_ID"))
  @ToString.Exclude
  BankProductData product;

  @Column(name = "TITLE", length = 4096)
  String title;

  @Column(name = "IMAGE_URI")
  @Convert(converter = URIDataConverter.class)
  URI imageUri;
  
  @PrePersist
  public void prePersist() {
    if (this.product() != null) {
      Set<BankProductCardArtData> set = new HashSet<BankProductCardArtData>();
      if (this.product().cardArt() != null) {
        set.addAll(this.product.cardArt());
      }
      set.add(this);
      this.product().cardArt(set);
    }
  }

}
