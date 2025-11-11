import { Routes } from '@angular/router';
import { UploadPageComponent } from './pages/upload-page/upload-page.component';
import { ProductsPageComponent } from './pages/products-page/products-page.component';
import { FaultyProductsComponent } from './pages/faulty-products/faulty-products.component';

export const routes: Routes = [
    { path: 'upload', component: UploadPageComponent },
    { path: 'products', component: ProductsPageComponent },
    { path: 'faulty-products', component: FaultyProductsComponent },
    { path: '', redirectTo: '/products', pathMatch: 'full' },
    { path: '**', redirectTo: '/products' }
];
