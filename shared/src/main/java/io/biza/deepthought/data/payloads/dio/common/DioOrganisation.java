package io.biza.deepthought.data.payloads.dio.common;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.cdr.CdrCommonOrganisation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Valid
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A Deep Thought Customer Container")
public class DioOrganisation {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Person Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Scheme Type", defaultValue = "CDR_COMMON")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.CDR_COMMON;

  @Schema(description = "Name of the organisation", required = true)
  @JsonProperty("businessName")
  @NotEmpty(message = "Business Name must be populated with the name of the organisation")
  @Valid
  String businessName;

  @Schema(description = "Legal name, if different to the business name")
  @JsonProperty("legalName")
  String legalName;

  @Schema(description = "Short name used for communication, if  different to the business name")
  @JsonProperty("shortName")
  String shortName;
  
  @Schema(description = "Preferred Address")
  @JsonProperty("address")
  DioAddress address;

  @JsonProperty("cdrCommon")
  @Schema(description = "CDR Common Organisation")
  @Valid
  @NotNull
  @NonNull
  public CdrCommonOrganisation cdrCommon;

}
