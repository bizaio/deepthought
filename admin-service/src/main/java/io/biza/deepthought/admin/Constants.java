package io.biza.deepthought.admin;

import java.util.Map;
import io.biza.babelfish.cdr.v1.enumerations.*;
import io.biza.deepthought.data.enumerations.FormFieldType;

public class Constants {
  public static final String OPENID_CONNECT_URL =
      "http://localhost:9090/auth/realms/master/.well-known/openid-configuration";

  public static final Map<FormFieldType, Class<?>> TYPE_FIELD_MAPPINGS = Map.ofEntries(
      Map.entry(FormFieldType.ADDRESS_PAF_FLAT_UNIT_TYPE, AddressPAFFlatUnitType.class),
      Map.entry(FormFieldType.ADDRESS_PAF_FLOOR_LEVEL_TYPE, AddressPAFFloorLevelType.class),
      Map.entry(FormFieldType.ADDRESS_PAF_POSTAL_DELIVERY_TYPE, AddressPAFPostalDeliveryType.class),
      Map.entry(FormFieldType.ADDRESS_PAF_STATE_TYPE, AddressPAFStateType.class),
      Map.entry(FormFieldType.ADDRESS_PAF_STREET_SUFFIX, AddressPAFStreetSuffix.class),
      Map.entry(FormFieldType.ADDRESS_PAF_STREET_TYPE, AddressPAFStreetType.class),
      Map.entry(FormFieldType.ADDRESS_PURPOSE, AddressPurpose.class),
      Map.entry(FormFieldType.BANKING_ACCOUNT_STATUS, BankingAccountStatus.class),
      Map.entry(FormFieldType.BANKING_ACCOUNT_STATUS_WITH_ALL, BankingAccountStatusWithAll.class),
      Map.entry(FormFieldType.BANKING_LOAN_REPAYMENT_TYPE, BankingLoanRepaymentType.class),
      Map.entry(FormFieldType.BANKING_PAYEE_TYPE, BankingPayeeType.class),
      Map.entry(FormFieldType.BANKING_PAYEE_TYPE_WITH_ALL, BankingPayeeTypeWithAll.class),
      Map.entry(FormFieldType.BANKING_PAYMENT_NON_BUSINESS_DAY_TREATMENT,
          BankingPaymentNonBusinessDayTreatment.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_CATEGORY, BankingProductCategory.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_CONSTRAINT_TYPE, BankingProductConstraintType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_DEPOSIT_RATE_TYPE,
          BankingProductDepositRateType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_DISCOUNT_ELIGIBILITY_TYPE,
          BankingProductDiscountEligibilityType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_DISCOUNT_TYPE, BankingProductDiscountType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_EFFECTIVE_WITH_ALL,
          BankingProductEffectiveWithAll.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_ELIGIBILITY_TYPE,
          BankingProductEligibilityType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_FEATURE_TYPE, BankingProductFeatureType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_FEE_TYPE, BankingProductFeeType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_LENDING_RATE_INTEREST_PAYMENT_TYPE,
          BankingProductLendingRateInterestPaymentType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_LENDING_RATE_TYPE,
          BankingProductLendingRateType.class),
      Map.entry(FormFieldType.BANKING_PRODUCT_RATE_TIER_APPLICATION_METHOD,
          BankingProductRateTierApplicationMethod.class),
      Map.entry(FormFieldType.BANKING_SCHEDULED_PAYMENT_STATUS,
          BankingScheduledPaymentStatus.class),
      Map.entry(FormFieldType.BANKING_TERM_DEPOSIT_MATURITY_INSTRUCTIONS,
          BankingTermDepositMaturityInstructions.class),
      Map.entry(FormFieldType.BANKING_TRANSACTION_SERVICE, BankingTransactionService.class),
      Map.entry(FormFieldType.BANKING_TRANSACTION_STATUS, BankingTransactionStatus.class),
      Map.entry(FormFieldType.BANKING_TRANSACTION_TYPE, BankingTransactionType.class),
      Map.entry(FormFieldType.COMMON_DISCOVERY_STATUS_TYPE, CommonDiscoveryStatusType.class),
      Map.entry(FormFieldType.COMMON_EMAIL_ADDRESS_PURPOSE, CommonEmailAddressPurpose.class),
      Map.entry(FormFieldType.COMMON_ORGANISATION_TYPE, CommonOrganisationType.class),
      Map.entry(FormFieldType.COMMON_PHONE_NUMBER_PURPOSE, CommonPhoneNumberPurpose.class),
      Map.entry(FormFieldType.COMMON_UNIT_OF_MEASURE_TYPE, CommonUnitOfMeasureType.class),
      Map.entry(FormFieldType.COMMON_WEEK_DAY, CommonWeekDay.class),
      Map.entry(FormFieldType.PAYLOAD_TYPE_ADDRESS, PayloadTypeAddress.class),
      Map.entry(FormFieldType.PAYLOAD_TYPE_BANKING_ACCOUNT, PayloadTypeBankingAccount.class),
      Map.entry(FormFieldType.PAYLOAD_TYPE_BANKING_DOMESTIC_PAYEE,
          PayloadTypeBankingDomesticPayee.class),
      Map.entry(FormFieldType.PAYLOAD_TYPE_BANKING_DOMESTIC_PAYEE_PAY_ID,
          PayloadTypeBankingDomesticPayeePayId.class),
      Map.entry(FormFieldType.PAYLOAD_TYPE_BANKING_PAYEE, PayloadTypeBankingPayee.class),
      Map.entry(FormFieldType.PAYLOAD_TYPE_BANKING_SCHEDULED_PAYMENT_RECURRENCE,
          PayloadTypeBankingScheduledPaymentRecurrence.class),
      Map.entry(FormFieldType.PAYLOAD_TYPE_BANKING_SCHEDULED_PAYMENT_TO,
          PayloadTypeBankingScheduledPaymentTo.class),
      Map.entry(FormFieldType.PAYLOAD_TYPE_CUSTOMER, PayloadTypeCustomer.class), Map.entry(
          FormFieldType.PAYLOAD_TYPE_TRANSACTION_EXTENSION, PayloadTypeTransactionExtension.class));
}
