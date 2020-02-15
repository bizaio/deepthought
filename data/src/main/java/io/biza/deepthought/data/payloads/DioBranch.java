package io.biza.deepthought.data.payloads;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.translation.converter.BSBStringToIntegerConverter;
import io.biza.deepthought.data.translation.converter.IntegerToBSBStringConverter;
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
@Schema(description = "A Deep Thought Branch Container")
public class DioBranch {

  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Bank Account Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "DIO_BANKING")
  public DioSchemeType schemeType;
  
  @Schema(
      description = "Australian Payments Clearing Association Number (aka BSB)",
      required = true, format = "string")
  @JsonSerialize(converter = BSBStringToIntegerConverter.class)
  @JsonDeserialize(converter = IntegerToBSBStringConverter.class)
  @NotEmpty(message = "Must contain a valid 6 digit number")
  @JsonProperty("bsb")
  @Min(100000)
  @Max(999999)
  Integer bsb;
  
  @Schema(description = "Bank Name")
  @JsonProperty("bankName")
  String bankName;
  
  @Schema(description = "Branch Name")
  @JsonProperty("branchName")
  String branchName;
  
  @Schema(description = "Branch Address")
  @JsonProperty("branchAddress")
  String branchAddress;
  
  @Schema(description = "Branch City")
  @JsonProperty("branchCity")
  String branchCity;
  
  @Schema(description = "Branch Postcode")
  @JsonProperty("branchPostcode")
  String branchPostcode;

}
