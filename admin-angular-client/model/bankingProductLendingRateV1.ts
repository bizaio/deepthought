/**
 * Deep Thought Administration API
 * This is the Deep Thought Administration API. You can find out more about Deep Thought at [https://github.com/bizaio/deepthought](https://github.com/bizaio/deepthought) or on the [DataRight.io Slack, #oss](https://join.slack.com/t/datarightio/shared_invite/enQtNzAyNTI2MjA2MzU1LTU1NGE4MmQ2N2JiZWI2ODA5MTQ2N2Q0NTRmYmM0OWRlM2U5YzA3NzU5NDYyODlhNjRmNzU3ZDZmNTI0MDE3NjY).
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { BankingProductLendingRateInterestPaymentType } from './bankingProductLendingRateInterestPaymentType';
import { BankingProductLendingRateType } from './bankingProductLendingRateType';
import { BankingProductRateTierV1 } from './bankingProductRateTierV1';

/**
 * Banking Product Lending Rate Definition
 */
export interface BankingProductLendingRateV1 { 
    lendingRateType: BankingProductLendingRateType;
    /**
     * The rate to be applied
     */
    rate: string;
    /**
     * A comparison rate equivalent for this rate
     */
    comparisonRate?: string;
    /**
     * The period after which the rate is applied to the balance to calculate the amount due for the period. Calculation of the amount is often daily (as balances may change) but accumulated until the total amount is 'applied' to the account (see applicationFrequency). Formatted according to [ISO 8601 Durations](https://en.wikipedia.org/wiki/ISO_8601#Durations)
     */
    calculationFrequency?: string;
    /**
     * The period after which the calculated amount(s) (see calculationFrequency) are 'applied' (i.e. debited or credited) to the account. Formatted according to [ISO 8601 Durations](https://en.wikipedia.org/wiki/ISO_8601#Durations)
     */
    applicationFrequency?: string;
    interestPaymentDue?: BankingProductLendingRateInterestPaymentType;
    /**
     * Rate tiers applicable for this rate
     */
    tiers?: Array<BankingProductRateTierV1>;
    /**
     * Generic field containing additional information relevant to the [lendingRateType](#tocSproductlendingratetypedoc) specified. Whether mandatory or not is dependent on the value of [lendingRateType](#tocSproductlendingratetypedoc)
     */
    additionalValue?: string;
    /**
     * Display text providing more information on the rate.
     */
    additionalInfo?: string;
    /**
     * Link to a web page with more information on this rate
     */
    additionalInfoUri?: string;
}