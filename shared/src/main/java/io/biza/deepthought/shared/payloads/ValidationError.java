package io.biza.deepthought.shared.payloads;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioValidationErrorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Valid
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "A Single Validation Error")
public class ValidationError {
  @JsonProperty("type")
  @NotNull
  @NonNull
  @Schema(description = "Validation Error Type")
  @Builder.Default
  DioValidationErrorType type = DioValidationErrorType.ATTRIBUTE_INVALID;
  
  @JsonProperty("fields")
  @Schema(description = "Fields which failed validation")
  List<String> fields;
  
  @JsonProperty("message")
  @NotNull
  @NonNull
  @Schema(description = "Validation Error Message")
  String message;
}
