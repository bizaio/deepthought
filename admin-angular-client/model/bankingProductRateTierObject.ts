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
import { BankingProductRateTierApplicabilityObject } from './bankingProductRateTierApplicabilityObject';
import { BankingProductRateTierApplicationMethod } from './bankingProductRateTierApplicationMethod';
import { CommonUnitOfMeasureType } from './commonUnitOfMeasureType';

/**
 * Defines the criteria and conditions for which a rate applies
 */
export interface BankingProductRateTierObject { 
    /**
     * A display name for the tier
     */
    name: string;
    unitOfMeasure: CommonUnitOfMeasureType;
    /**
     * The number of tierUnitOfMeasure units that form the lower bound of the tier. The tier should be inclusive of this value
     */
    minimumValue: number;
    /**
     * The number of tierUnitOfMeasure units that form the upper bound of the tier or band. For a tier with a discrete value (as opposed to a range of values e.g. 1 month) this must be the same as tierValueMinimum. Where this is the same as the tierValueMinimum value of the next-higher tier the referenced tier should be exclusive of this value. For example a term deposit of 2 months falls into the upper tier of the following tiers: (1 – 2 months, 2 – 3 months). If absent the tier's range has no upper bound.
     */
    maximumValue?: number;
    rateApplicationMethod?: BankingProductRateTierApplicationMethod;
    applicabilityConditions?: BankingProductRateTierApplicabilityObject;
}