import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonUnitOfMeasureType } from '@bizaoss/deepthought-admin-angular-client';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import * as moment from 'moment';
import { from, Observable, of, zip } from 'rxjs';
import { distinct, filter, groupBy, map, mergeMap, pluck, reduce, switchMap, toArray } from 'rxjs/operators';

interface IRateConditions {
    applicationFrequency: string;
    calculationFrequency: string;
}

@Component({
  selector: 'app-rates-table',
  templateUrl: './rates-table.component.html',
})
export class RatesTableComponent implements OnChanges {

    conditionsOptions = {
        applicationFrs: [],
        calculationFrs: [],
    };
    conditions: IRateConditions = { applicationFrequency: '', calculationFrequency: '' };

    rates$: Observable<any> = null;
    columns$: Observable<any> = null;

    @Input() fixedRates: any[];

    constructor(public typeUtilityService: TypeUtilityService) { }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.fixedRates) {
            const getFrequenciesList = (frequencyType: 'applicationFrequency' | 'calculationFrequency') => {
                return from(changes.fixedRates.currentValue).pipe(
                    map((rate: any) => ({ ...rate.cdrBanking, id: rate.id })),
                    pluck(frequencyType),
                    distinct(),
                    filter((value) => !!value),
                    map((value: string) => ({
                        value,
                        label: this.typeUtilityService.convertDuration(value),
                        asDays: moment.duration(value).asDays(),
                    })),
                    toArray(),
                    map((values) => values.sort((a, b) => a.asDays - b.asDays))
                );
            };

            // fill conditions
            getFrequenciesList('applicationFrequency').subscribe((data) => {
                this.conditionsOptions.applicationFrs = data;
                if (data && data[0]) {
                    this.conditions.applicationFrequency = data[0].value;
                }
            });

            getFrequenciesList('calculationFrequency').subscribe((data) => {
                this.conditionsOptions.calculationFrs = data;
                if (data && data[0]) {
                    this.conditions.calculationFrequency = data[0].value;
                }
            });

            this.handleConditionsChange();
        }
    }

    handleConditionsChange() {

        const stream$ = from(this.fixedRates)
            .pipe(
                map((rate: any) => ({ ...rate.cdrBanking, id: rate.id })),
                filter((rate: any) => this.conditions.applicationFrequency ? rate.applicationFrequency === this.conditions.applicationFrequency : true),
                filter((rate: any) => this.conditions.calculationFrequency ? rate.calculationFrequency === this.conditions.calculationFrequency : true),
                map((rate: any) => {

                    const filteredTiers = (rate.tiers && rate.tiers.length)
                        ? rate.tiers
                            .filter((tier) => tier.unitOfMeasure === CommonUnitOfMeasureType.DOLLAR)
                            .map((tier) => ({
                                ...tier,
                                name: `${this.typeUtilityService.convertValueString(tier.minimumValue)} - ${this.typeUtilityService.convertValueString(tier.maximumValue)}`,
                            }))
                        : null;

                    return {
                        rate: rate.rate, // Math.round(parseFloat(dataRow.rate) * 10000) / 100
                        tier: (filteredTiers && filteredTiers.length) ? filteredTiers[0] : { name: 'Initial Tier', minimumValue: 0, maximumValue: 0 },
                        additionalValue: rate.additionalValue
                    };
                })
            );


        this.columns$ = stream$.pipe(
            pluck('additionalValue'),
            distinct(),
            map((value) => ({
                value,
                label: this.typeUtilityService.convertDuration(value),
                asDays: moment.duration(value).asDays()
            })),
            toArray(),
            map((values) => values.sort((a, b) => a.asDays - b.asDays))
        );


        this.rates$ = stream$.pipe(

            // group by tier name
            groupBy((rate: any) => rate.tier.name),
            mergeMap((group) => zip(of(group.key), group.pipe(toArray()))),

            // sort by tier minimumValue
            toArray(),
            map((values) => values.sort((a: any, b: any) => a[1][0].tier.minimumValue - b[1][0].tier.minimumValue)),
            switchMap((rates) => from(rates)),


            // group tier rates by additionalValue
            mergeMap(([key, group]) =>
                from(group).pipe(
                    groupBy((rate: any) => rate.additionalValue),
                    mergeMap((group2) => zip(of(group2.key), group2.pipe(toArray()))),
                    reduce((acc, [key2, group2]) => {
                        acc[key2] = group2[0].rate;
                        return acc;
                    }, {}),
                    mergeMap((group2) => zip(of(key), of(group2)))
                )
            ),

            // transform to object
            map(([tier, rates]) => ({ tier, rates })),
            toArray()
        );

    }

}