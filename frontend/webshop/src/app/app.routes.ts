import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { ProductsComponent } from './pages/products/products.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { ProductDetailsComponent } from './pages/product-details/product-details.component';
import { CartComponent } from './pages/cart/cart.component';
import { PurchaseComponent } from './pages/purchase/purchase.component';

export const routes: Routes = [
    { path: 'products', component: ProductsComponent },
    { path: 'product-details/:id', component: ProductDetailsComponent },
    { path: 'home', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'about-us', component: AboutUsComponent },
    { path: 'cart', component: CartComponent },
    { path: 'purchase', component: PurchaseComponent },
    { path: 'purchase/capture', component: PurchaseComponent },
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: '**', component: HomeComponent },
];
