import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from '@app/shared/shared.module';

import { CatalogueProductViewComponent } from './product-view/catalogue-product-view.component';
import { ProductViewDetailsComponent } from './product-view/product-view-details/product-view-details.component';
import { ProductViewFeaturesComponent } from './product-view/product-view-features/product-view-features.component';
import { BrandsRoutes } from './brands.routes';
import { BrandsListComponent } from './brands-list/brands-list.component';
import { BrandViewComponent } from './brand-view/brand-view.component';
import { ProductViewConstrainsComponent } from './product-view/product-view-constrains/product-view-constrains.component';
import { ProductViewFeesComponent } from './product-view/product-view-fees/product-view-fees.component';
import { ProductViewRatesLendingComponent } from './product-view/rates/product-view-rates-lending/product-view-rates-lending.component';
import { ProductViewRatesDepositComponent } from './product-view/rates/product-view-rates-deposit/product-view-rates-deposit.component';
import { RatesTableComponent } from './product-view/rates/rates-table/rates-table.component';
import { ProductCreateEditComponent } from './product-create-edit/product-create-edit.component';
import { DialogService } from 'primeng/api';
import { ProductFeatureCreateEditComponent } from './product-view/product-view-features/product-feature-create-edit/product-feature-create-edit.component';
import { ProductEligibilityCreateEditComponent } from './product-view/product-view-constrains/product-eligibility-create-edit/product-eligibility-create-edit.component';
import { ProductConstraintCreateEditComponent } from './product-view/product-view-constrains/product-constraint-create-edit/product-constraint-create-edit.component';
import { ProductFeeCreateEditComponent } from './product-view/product-view-fees/product-fee-create-edit/product-fee-create-edit.component';
import { BrandCreateEditComponent } from './brand-create-edit/brand-create-edit.component';
import { BrandViewProductsComponent } from './brand-view/brand-view-products/brand-view-products.component';
import { BrandViewBundlesComponent } from './brand-view/brand-view-bundles/brand-view-bundles.component';
import { BundleCreateEditComponent } from './bundle-create-edit/bundle-create-edit.component';
import { ProductRateDepositCreateEditComponent } from './product-view/rates/product-view-rates-deposit/product-rate-deposit-create-edit/product-rate-deposit-create-edit.component';
import { ProductRateLendingCreateEditComponent } from './product-view/rates/product-view-rates-lending/product-rate-lending-create-edit/product-rate-lending-create-edit.component';
import { BundleViewComponent } from './bundle-view/bundle-view.component';
import { BundleAddProductComponent } from './bundle-view/bundle-add-product/bundle-add-product.component';

@NgModule({
    declarations: [
        BrandsListComponent,
        CatalogueProductViewComponent,
        RatesTableComponent,
        ProductViewDetailsComponent,
        ProductViewFeaturesComponent,
        BrandViewComponent,
        ProductViewConstrainsComponent,
        ProductViewFeesComponent,
        ProductViewRatesLendingComponent,
        ProductViewRatesDepositComponent,
        ProductCreateEditComponent,
        ProductFeatureCreateEditComponent,
        ProductEligibilityCreateEditComponent,
        ProductConstraintCreateEditComponent,
        ProductFeeCreateEditComponent,
        BrandCreateEditComponent,
        BrandViewProductsComponent,
        BrandViewBundlesComponent,
        BundleCreateEditComponent,
        ProductRateDepositCreateEditComponent,
        ProductRateLendingCreateEditComponent,
        BundleViewComponent,
        BundleAddProductComponent
    ],
    imports: [
        CommonModule,
        SharedModule,
        BrandsRoutes,
    ],
    entryComponents: [
        ProductCreateEditComponent,
        ProductFeatureCreateEditComponent,
        ProductEligibilityCreateEditComponent,
        ProductConstraintCreateEditComponent,
        ProductFeeCreateEditComponent,
        BrandCreateEditComponent,
        BundleCreateEditComponent,
        ProductRateDepositCreateEditComponent,
        ProductRateLendingCreateEditComponent,
        BundleAddProductComponent
    ],
    providers: [
        DialogService
    ]
})
export class BrandsModule {}
