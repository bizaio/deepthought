/*******************************************************************************
 * Copyright (C) 2020 Biza Pty Ltd
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *******************************************************************************/
package io.biza.deepthought.data.payloads.dio.banking;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.biza.babelfish.cdr.converters.AmountStringToBigDecimalConverter;
import io.biza.babelfish.cdr.converters.BigDecimalToAmountStringConverter;
import io.biza.babelfish.cdr.converters.CurrencyToStringConverter;
import io.biza.babelfish.cdr.converters.DurationToStringConverter;
import io.biza.babelfish.cdr.converters.LocalDateToStringConverter;
import io.biza.babelfish.cdr.converters.StringToCurrencyConverter;
import io.biza.babelfish.cdr.converters.StringToLocalDateConverter;
import io.biza.babelfish.cdr.enumerations.BankingLoanRepaymentType;
import io.biza.deepthought.data.enumerations.DioLoanRepaymentType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Valid
@ToString
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Banking Loan Account Details", name = "DioBankAccountLoanAccount")
public class DioBankAccountLoanAccount {
  
  @JsonProperty("id")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Bank Account Loan Identifier",
      defaultValue = "00000000-0000-0000-0000-000000000000")
  @Builder.Default
  public UUID id = new UUID(0, 0);

  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Deep Thought Scheme Type", defaultValue = "DIO_BANKING")
  @Builder.Default
  public DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @Schema(description = "Original Loan Start Date", format = "date-time")
  @JsonProperty("creationDateTime")
  OffsetDateTime creationDateTime;

  @Schema(description = "Original Loan Value")
  @JsonProperty("creationAmount")
  BigDecimal creationAmount;

  @Schema(description = "Original Loan Value Currency")
  @JsonSerialize(converter = CurrencyToStringConverter.class)
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  @JsonProperty(value = "currency", defaultValue = "AUD")
  @Builder.Default
  Currency currency = Currency.getInstance("AUD");
  
  @Schema(description = "Original length at creation", required = true)
  @NotNull
  @JsonProperty("creationLength")
  Period creationLength;
  
  @Schema(description = "Repayment Frequency")
  @NotNull
  @JsonProperty("repaymentFrequency")
  Period repaymentFrequency;
  
  @Schema(description = "Last Repayment")
  @NotNull
  @JsonProperty("lastRepayment")
  OffsetDateTime lastRepayment;
  
  @Schema(description = "Next Repayment Amount")
  @NotNull
  @JsonProperty("nextRepaymentAmount")
  BigDecimal nextRepaymentAmount;
  
  @Schema(description = "Redraw Value Available")
  @NotNull
  @JsonProperty("redrawAvailable")
  @Builder.Default
  BigDecimal redrawAvailable = BigDecimal.ZERO;
  
  @Schema(description = "Repayment Type")
  @NotNull
  @JsonProperty("repaymentType")
  DioLoanRepaymentType repaymentType;

}
