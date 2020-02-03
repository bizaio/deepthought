import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, Validators } from '@angular/forms';
import {
    CdrFormDuration,
    CdrFormInput,
    CdrFormSelect
} from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { DynamicDialogConfig, DynamicDialogRef, SelectItem } from 'primeng/api';
import {
    BankingProductDepositRate,
    BankingProductDepositRateType,
    BankingProductRateTierApplicationMethod,
    BankingProductRateTierObject,
    CommonUnitOfMeasureType,
    DioProductRateDeposit,
    DioSchemeType,
    FormFieldType,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';
import { TypeManagementService } from '@app/core/services/type-management.service';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';

@Component({
  selector: 'app-product-rate-create-edit',
  templateUrl: './product-rate-deposit-create-edit.component.html',
  styleUrls: ['./product-rate-deposit-create-edit.component.scss']
})
export class ProductRateDepositCreateEditComponent implements OnInit {

    brandId: string;
    productId: string;

    rateForm = new CdrFormGroup({
        id:             new CdrFormInput('', '', [Validators.required]),
        schemeType:     new CdrFormSelect(null, 'Scheme type', [Validators.required], []),
    });

    cdrBankingForm = new CdrFormGroup({
        depositRateType:        new CdrFormSelect('', 'Rate type', [Validators.required]),
        rate:                   new CdrFormInput('', 'Rate'),
        calculationFrequency:   new CdrFormDuration('', 'Calculation frequency'),
        applicationFrequency:   new CdrFormDuration('', 'Application frequency'),
        additionalValue:        new CdrFormInput('', 'Additional value'),
        additionalInfo:         new CdrFormInput('', 'Additional info'),
        additionalInfoUri:      new CdrFormInput('', 'Additional info URI'),
    });

    tiersForm = new FormArray([]);

    rate: DioProductRateDeposit;

    unitOfMeasureOptions: SelectItem[] = [];
    rateTierApplicationMethodOptions: SelectItem[] = [];

    constructor(
        private ref: DynamicDialogRef,
        private config: DynamicDialogConfig,
        private productsApi: ProductAdminService,
        private typeManager: TypeManagementService
    ) {}

    ngOnInit() {
        this.rateForm.addControl('cdrBanking', this.cdrBankingForm);

        const idControl = this.rateForm.controls.id as CdrFormInput;
        idControl.isVisible = false;

        // *********************************************************************************************************************************

        const schemeTypeOptions = Object.keys(DioSchemeType).map((key) => ({
            value: DioSchemeType[key],
            label: DioSchemeType[key],
        }));

        const schemeTypeOptionsControl = this.rateForm.controls.schemeType as CdrFormSelect;
        schemeTypeOptionsControl.options = schemeTypeOptions;
        schemeTypeOptionsControl.setValue(schemeTypeOptions[0].value);
        schemeTypeOptionsControl.disable();

        // *********************************************************************************************************************************

        const rateTypeOptions = Object.keys(BankingProductDepositRateType).map((key) => ({
            value: BankingProductDepositRateType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTDEPOSITRATETYPE, BankingProductDepositRateType[key]),
        }));

        const rateTypeControl = this.cdrBankingForm.controls.depositRateType as CdrFormSelect;
        rateTypeControl.options = rateTypeOptions;
        rateTypeControl.setValue(rateTypeOptions[0].value);

        // *********************************************************************************************************************************

        this.unitOfMeasureOptions = Object.keys(CommonUnitOfMeasureType).map((key) => ({
            value: CommonUnitOfMeasureType[key],
            label: this.typeManager.getLabel(FormFieldType.COMMONUNITOFMEASURETYPE, CommonUnitOfMeasureType[key]),
        }));

        this.rateTierApplicationMethodOptions = Object.keys(BankingProductRateTierApplicationMethod).map((key) => ({
            value: BankingProductRateTierApplicationMethod[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTRATETIERAPPLICATIONMETHOD, BankingProductRateTierApplicationMethod[key]),
        }));

        // *********************************************************************************************************************************

        this.getConfigProp('brandId', true);
        this.getConfigProp('productId', true);
        this.getConfigProp('rate');

        if (this.rate) {
            this.fillForm(this.rate);
        } else {
            this.rateForm.removeControl('id');
            this.addTier();
        }
    }

    getConfigProp(propName, required = false) {
        if (this.config.data && this.config.data[propName]) {
            this[propName] = this.config.data[propName];
        } else if (required) {
            this.ref.close(null);
            throw new Error(`'${propName}' is required param`);
        }
    }

    fillForm(depositRate: DioProductRateDeposit) {
        const {
            id,
            schemeType,
            cdrBanking = {} as BankingProductDepositRate
        } = depositRate;

        this.rateForm.controls.id.setValue(id);
        this.rateForm.controls.schemeType.setValue(schemeType);

        const {
            depositRateType = '',
            rate = '',
            calculationFrequency = '',
            applicationFrequency = '',
            tiers = [],
            additionalValue = '',
            additionalInfo = '',
            additionalInfoUri = '',
        } = cdrBanking;

        this.cdrBankingForm.controls.depositRateType.setValue(depositRateType);
        this.cdrBankingForm.controls.rate.setValue(rate);
        this.cdrBankingForm.controls.calculationFrequency.setValue(calculationFrequency);
        this.cdrBankingForm.controls.applicationFrequency.setValue(applicationFrequency);
        this.cdrBankingForm.controls.additionalValue.setValue(additionalValue);
        this.cdrBankingForm.controls.additionalInfo.setValue(additionalInfo);
        this.cdrBankingForm.controls.additionalInfoUri.setValue(additionalInfoUri);

        if (tiers && tiers.length) {
            for (const tier of tiers) {
                this.addTier(tier);
            }
        }
    }

    isTiersAvailable(): boolean {
        return this.cdrBankingForm.get('depositRateType').value === BankingProductDepositRateType.FIXED;
    }

    addTier(tier: BankingProductRateTierObject = {} as BankingProductRateTierObject) {
        const {
            name = '',
            unitOfMeasure = this.unitOfMeasureOptions[0].value,
            rateApplicationMethod = this.rateTierApplicationMethodOptions[0].value,
            minimumValue = 0,
            maximumValue = 0,
            applicabilityConditions: {
                additionalInfo = '',
                additionalInfoUri = ''
            } = {}
        } = tier;

        this.tiersForm.push(new CdrFormGroup({
            name:                   new CdrFormInput(name, 'Name'),
            unitOfMeasure:          new CdrFormSelect(unitOfMeasure, 'Unit of measure', [], this.unitOfMeasureOptions),
            rateApplicationMethod:  new CdrFormSelect(rateApplicationMethod, 'Rate application method', [], this.rateTierApplicationMethodOptions),
            minimumValue:           new CdrFormInput(minimumValue, 'Minimum value', [], 'number'),
            maximumValue:           new CdrFormInput(maximumValue, 'Maximum value', [], 'number'),
            applicabilityConditions: new CdrFormGroup({
                additionalInfo: new CdrFormInput(additionalInfo, 'Additional info'),
                additionalInfoUri: new CdrFormInput(additionalInfoUri, 'Additional info URI')
            })
        }));
    }

    removeTier(index: number) {
        this.tiersForm.removeAt(index);
    }

    onCancel() {
        this.ref.close(null);
    }

    onSave() {
        this.rateForm.setSubmitted(true);

        if (!this.rateForm.valid) {
            return;
        }

        const rateData = this.rateForm.getRawValue();

        if (this.isTiersAvailable()) {
            rateData.cdrBanking.tiers = this.tiersForm.value;
        }

        const saving$ = this.rate
            ? this.productsApi.updateProductRateDeposit(this.brandId, this.productId, this.rate.id, rateData)
            : this.productsApi.createProductRateDeposit(this.brandId, this.productId, rateData)
        ;

        saving$.subscribe(
            (rate) => this.ref.close(rate),
            this.onSavingError.bind(this)
        );
    }

    onSavingError({ error: { type, validationErrors: errors } }) {
        if (type === 'VALIDATION_ERROR') {

            const mapErrorFieldControl: { [key: string]: AbstractControl } = {
                'cdrBanking.lendingRateType':       this.cdrBankingForm.get('lendingRateType'),
                'cdrBanking.rate':                  this.cdrBankingForm.get('rate'),
                'cdrBanking.calculationFrequency':  this.cdrBankingForm.get('calculationFrequency'),
                'cdrBanking.applicationFrequency':  this.cdrBankingForm.get('applicationFrequency'),
                'cdrBanking.additionalValue':       this.cdrBankingForm.get('additionalValue'),
                'cdrBanking.additionalInfo':        this.cdrBankingForm.get('additionalInfo'),
                'cdrBanking.additionalInfoUri':     this.cdrBankingForm.get('additionalInfoUri'),
            };

            for (let i = 0; i < this.tiersForm.controls.length; i++) {
                mapErrorFieldControl[`cdrBanking.tiers[${i}].name`] = this.tiersForm.controls[i].get('name');
                mapErrorFieldControl[`cdrBanking.tiers[${i}].unitOfMeasure`] = this.tiersForm.controls[i].get('unitOfMeasure');
                mapErrorFieldControl[`cdrBanking.tiers[${i}].rateApplicationMethod`] = this.tiersForm.controls[i].get('rateApplicationMethod');
                mapErrorFieldControl[`cdrBanking.tiers[${i}].minimumValue`] = this.tiersForm.controls[i].get('minimumValue');
                mapErrorFieldControl[`cdrBanking.tiers[${i}].maximumValue`] = this.tiersForm.controls[i].get('maximumValue');
            }

            for (const error of errors) {
                for (const field of error.fields) {
                    if (mapErrorFieldControl[field]) {
                        mapErrorFieldControl[field].setErrors({ SERVER: error.message });
                    }
                }
            }
        }
    }

    trackByFn(index, item) {
        return index;
    }
}
