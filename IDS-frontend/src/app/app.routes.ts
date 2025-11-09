import { Routes } from '@angular/router';
import { UploadPageComponent } from './pages/upload-page/upload-page.component';
import { ProductsPageComponent } from './pages/products-page/products-page.component';

export const routes: Routes = [
    { path: 'upload', component: UploadPageComponent },
    { path: 'products', component: ProductsPageComponent },
    { path: '', redirectTo: '/products', pathMatch: 'full' },
    { path: '**', redirectTo: '/products' }
];
