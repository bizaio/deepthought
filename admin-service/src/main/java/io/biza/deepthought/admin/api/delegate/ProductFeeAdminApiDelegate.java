package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.product.DioProductFee;

public interface ProductFeeAdminApiDelegate {
  
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioProductFee>> listProductFees(UUID brandId, UUID productId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductFee> getProductFee(UUID brandId, UUID productId, UUID feeId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductFee> createProductFee(UUID brandId, UUID productId,
      DioProductFee fee) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteProductFee(UUID brandId, UUID productId, UUID feeId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductFee> updateProductFee(UUID brandId, UUID productId, UUID feeId,
      DioProductFee fee) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
