package io.biza.deepthought.admin.api;

import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.ProductCardArtAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.DioProductCardArt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Tag(name = Labels.TAG_PRODUCT_NAME, description = Labels.TAG_PRODUCT_DESCRIPTION)
@RequestMapping("/v1/brand/{brandId}/product/{productId}/card-art")
public interface ProductCardArtAdminApi {

  default ProductCardArtAdminApiDelegate getDelegate() {
    return new ProductCardArtAdminApiDelegate() {};
  }

  @Operation(summary = "List all Product Card Arts", description = "List all Product Card Arts",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_READ})})
  @ApiResponses(value = {@ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
      description = Labels.RESPONSE_SUCCESSFUL_LIST, content = @Content(
          array = @ArraySchema(schema = @Schema(implementation = DioProductCardArt.class))))})
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_READ)
  default ResponseEntity<List<DioProductCardArt>> listProductCardArts(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId) {
    return getDelegate().listProductCardArts(brandId, productId);
  }

  @Operation(summary = "Get a single Product Card Art",
      description = "Returns a single product Card Art entry",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_READ})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_READ,
          content = @Content(schema = @Schema(implementation = DioProductCardArt.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @GetMapping(value = "/{cardArtId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_READ)
  default ResponseEntity<DioProductCardArt> getProductCardArt(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId,
      @NotNull @Valid @PathVariable("cardArtId") UUID cardArtId) {
    return getDelegate().getProductCardArt(brandId, productId, cardArtId);
  }

  @Operation(summary = "Create a Product Card Art",
      description = "Creates and Returns a new Product Card Art",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_CREATE,
          content = @Content(schema = @Schema(implementation = DioProductCardArt.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<DioProductCardArt> createProductCardArt(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId,
      @NotNull @RequestBody DioProductCardArt cardArt) throws ValidationListException {
    return getDelegate().createProductCardArt(brandId, productId, cardArt);
  }

  @Operation(summary = "Update a single Product Card Art",
      description = "Updates and Returns an existing Product Card Art",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_OK,
          description = Labels.RESPONSE_SUCCESSFUL_UPDATE,
          content = @Content(schema = @Schema(implementation = DioProductCardArt.class))),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_UNPROCESSABLE_ENTITY,
          description = Labels.RESPONSE_INPUT_VALIDATION_ERROR, content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ValidationListException.class))))})
  @PutMapping(path = "/{cardArtId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<DioProductCardArt> updateProductCardArt(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId,
      @NotNull @Valid @PathVariable("cardArtId") UUID cardArtId,
      @NotNull @RequestBody DioProductCardArt cardArt) throws ValidationListException {
    return getDelegate().updateProductCardArt(brandId, productId, cardArtId, cardArt);
  }

  @Operation(summary = "Delete a single Product Card Art", description = "Deletes a Product Card Art",
      security = {@SecurityRequirement(name = Labels.SECURITY_SCHEME_NAME,
          scopes = {Labels.SECURITY_SCOPE_PRODUCT_WRITE})})
  @ApiResponses(value = {
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NO_CONTENT,
          description = Labels.RESPONSE_SUCCESSFUL_DELETE),
      @ApiResponse(responseCode = Labels.RESPONSE_CODE_NOT_FOUND,
          description = Labels.RESPONSE_OBJECT_NOT_FOUND)})
  @DeleteMapping(path = "/{cardArtId}")
  @PreAuthorize(Labels.OAUTH2_SCOPE_PRODUCT_WRITE)
  default ResponseEntity<Void> deleteProductCardArt(
      @NotNull @Valid @PathVariable("brandId") UUID brandId,
      @NotNull @Valid @PathVariable("productId") UUID productId,
      @NotNull @Valid @PathVariable("cardArtId") UUID cardArtId) {
    return getDelegate().deleteProductCardArt(brandId, productId, cardArtId);
  }


}
