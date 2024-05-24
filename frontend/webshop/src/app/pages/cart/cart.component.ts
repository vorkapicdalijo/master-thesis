import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { CartItem } from '../../models/cart-item';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { environment } from '../../../environment/environment';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    MatListModule, 
    MatDividerModule, 
    MatIconModule, 
    MatButtonModule,
    MatFormFieldModule,
    FormsModule,
    MatSelectModule,],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss',
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];

  amounts: number[] = Array.from({ length: 10 }, (_, i) => i + 1);
  selectedAmount: number = 1;
  isCheckingProductsAvailability: boolean = false;

  areAllProductsAvailable: boolean = false;


  constructor(private cartService: CartService, private router: Router, private orderService: OrderService) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
    this.checkProductsAvailability();
  }

  public getCartItemPrice(item: CartItem): number {
    return item.amount*item.price;
  }

  public amountSelectionChange() {
    this.checkProductsAvailability();

    this.cartService.cartItems = this.cartItems;
    this.cartService.saveCart();
    this.cartService.cartSub.next(true);
  }

  public removeItemFromCart(index: number) {
    this.cartItems.splice(index, 1);
    this.cartService.cartItems = this.cartItems;
    this.cartService.saveCart();
    this.cartService.cartSub.next(true);
  }

  public calculateTotalPrice(): number {
    let total = 0;
    this.cartItems.forEach(item => {
      total = total + (item.amount*item.price);
    });

    return total;
  }

  public navigateToProductsPage() {
    this.router.navigateByUrl('/products');
  }

  public checkProductsAvailability() {
    this.isCheckingProductsAvailability = true;

    this.cartItems.forEach(product => {
      this.orderService.checkProductAvailability(product.productId, product.amount)
        .subscribe((isAvailable: boolean) => {
          product.isAvailable = isAvailable;

          this.areAllProductsAvailable = this.cartItems.every(obj => obj.isAvailable == true);
        });
    });

    this.isCheckingProductsAvailability = false;
  }


  public navigateToPurchasePage() {
    this.router.navigateByUrl('/purchase');
  }

  public getImageUrl(imageName: string) {
    return environment.imageBaseUrl + imageName;
  }
}
