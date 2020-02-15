package io.biza.deepthought.data.payloads.cdr;

import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Locale;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.babelfish.cdr.converters.CountryStringToLocaleConverter;
import io.biza.babelfish.cdr.converters.DateTimeStringToOffsetDateTimeConverter;
import io.biza.babelfish.cdr.converters.LocalDateToStringConverter;
import io.biza.babelfish.cdr.converters.LocaleToCountryStringConverter;
import io.biza.babelfish.cdr.converters.OffsetDateTimeToDateTimeStringConverter;
import io.biza.babelfish.cdr.converters.StringToLocalDateConverter;
import io.biza.babelfish.cdr.converters.UriStringToUriConverter;
import io.biza.babelfish.cdr.converters.UriToUriStringConverter;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.enumerations.CommonOrganisationType;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductAdditionalInformationV1;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Valid
@Data
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "CDR Organisation Attributes")
public class CdrCommonOrganisation {

  @Schema(description = "Australian Business Number for the organisation")
  @JsonProperty("abn")
  String abn;

  @Schema(
      description = "Australian Company Number for the organisation. Required only if an ACN is applicable for the organisation type")
  @JsonProperty("acn")
  String acn;

  @Schema(
      description = "True if registered with the ACNC.  False if not. Absent or null if not confirmed.")
  @JsonProperty("isACNCRegistered")
  Boolean isACNCRegistered;

  @Schema(description = "[ANZSIC (2006)](http://www.abs.gov.au/anzsic) code for the organisation.")
  @JsonProperty("industryCode")
  String industryCode;

  @Schema(description = "Legal organisation type", required = true)
  @JsonProperty("organisationType")
  @NotNull
  @Valid
  CommonOrganisationType organisationType;

  @Schema(
      description = "Enumeration with values from [ISO 3166 Alpha-3](https://www.iso.org/iso-3166-country-codes.html) country codes.  Assumed to be AUS if absent")
  @JsonSerialize(converter = LocaleToCountryStringConverter.class)
  @JsonDeserialize(converter = CountryStringToLocaleConverter.class)
  @JsonProperty("registeredCountry")
  Locale registeredCountry;

  @Schema(description = "The date the organisation described was established", type = "string")
  @JsonSerialize(converter = LocalDateToStringConverter.class)
  @JsonDeserialize(converter = StringToLocalDateConverter.class)
  @JsonProperty("establishmentDate")
  LocalDate establishmentDate;


}
