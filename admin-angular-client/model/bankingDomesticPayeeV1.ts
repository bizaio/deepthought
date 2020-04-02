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
import { BankingDomesticPayeeAccountV1 } from './bankingDomesticPayeeAccountV1';
import { BankingDomesticPayeeCardV1 } from './bankingDomesticPayeeCardV1';
import { BankingDomesticPayeePayIdV1 } from './bankingDomesticPayeePayIdV1';
import { PayloadTypeBankingDomesticPayee } from './payloadTypeBankingDomesticPayee';

/**
 * Representation of a Domestic Payee Detail
 */
export interface BankingDomesticPayeeV1 { 
    payeeAccountUType: PayloadTypeBankingDomesticPayee;
    account?: BankingDomesticPayeeAccountV1;
    card?: BankingDomesticPayeeCardV1;
    payId?: BankingDomesticPayeePayIdV1;
}