import { CanActivateFn, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { ProductsComponent } from './pages/products/products.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { ProductDetailsComponent } from './pages/product-details/product-details.component';
import { CartComponent } from './pages/cart/cart.component';
import { PurchaseComponent } from './pages/purchase/purchase.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { AuthGuard } from './guards/auth.guard';
import { inject } from '@angular/core';


export const routes: Routes = [
    { path: 'products', component: ProductsComponent },
    { path: 'product-details/:id', component: ProductDetailsComponent },
    { path: 'home', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'about-us', component: AboutUsComponent },
    { path: 'cart', component: CartComponent },
    { path: 'orders', component: OrdersComponent, canActivate:[AuthGuard] },
    { path: 'purchase', component: PurchaseComponent, canActivate:[AuthGuard] },
    { path: 'purchase/capture', component: PurchaseComponent, canActivate:[AuthGuard] },
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: '**', component: HomeComponent },
];
