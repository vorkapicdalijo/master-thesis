import { Injectable, PLATFORM_ID, inject } from '@angular/core';
import { CartItem } from '../models/cart-item';
import { AuthService } from './auth.service';
import { isPlatformBrowser } from '@angular/common';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartSub: Subject<boolean> = new Subject();

  cartItems: CartItem[] = [];
  private readonly platformId = inject(PLATFORM_ID);

  constructor(private authService: AuthService) {
    
   }

  public addToCart(addedItem: CartItem) {
    this.loadCart();
    let itemExistsInCart: boolean = false;
    this.cartItems.forEach(item => {
      if (item.id == addedItem.id) {
        itemExistsInCart = true;
        item.amount = item.amount + addedItem.amount;
      }
    })
    if(!itemExistsInCart) {
      this.cartItems.push(addedItem);
    }

    this.saveCart();

    this.cartSub.next(true);
  }

  public saveCart() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(`cart_items_${this.authService.userId}`,
        JSON.stringify(this.cartItems)
      );
    }
  }

  public getCartItems() {
    this.loadCart();
    return this.cartItems;
  }

  public loadCart() {
    if (isPlatformBrowser(this.platformId)) {
      this.cartItems = JSON.parse(localStorage.getItem(`cart_items_${this.authService.userId}`)!) ?? [];
    }
  }

  public clearCart() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(`cart_items_${this.authService.userId}`);
    }
  }

  public removeCartItem(itemId: number) {
    const index = this.cartItems.findIndex(
      item => item.id == itemId
    );

    if (index > -1) {
      this.cartItems.splice(index, 1);
      this.saveCart();

      this.cartSub.next(true);
    }
  }

  public getCartItemCount(): number {
    this.loadCart();

    let count = 0;
    this.cartItems.forEach(item => {
      count = count + item.amount;
    });
    return count;
  }


}
