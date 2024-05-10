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

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
  }

  public getCartItemPrice(item: CartItem): number {
    return item.amount*item.price;
  }

  public amountSelectionChange() {
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
}
