package io.biza.deepthought.data.payloads;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.data.enumerations.DioPhoneType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
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
@Schema(description = "A Deep Thought Phone Number Container")
public class DioPhoneNumber {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Person Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @Schema(description = "Scheme Type", defaultValue = "DIO_COMMON")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.DIO_COMMON;
  
  @JsonProperty("isPreferred")
  @NotNull
  @Schema(description = "Preferred Number", defaultValue = "false")
  @Builder.Default
  Boolean isPreferred = false;
  
  @JsonProperty("phoneType")
  @Schema(description = "Phone Type")
  DioPhoneType phoneType;
  
  @JsonProperty("fullNumber")
  @Schema(description = "Phone Number in RFC3966 Format")
  @NotNull
  // TODO: Add validator
  String fullNumber;  
  
}
