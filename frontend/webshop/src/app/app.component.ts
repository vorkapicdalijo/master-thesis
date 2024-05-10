import { AfterViewInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  Router,
  RouterModule,
  RouterOutlet,
} from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MediaMatcher } from '@angular/cdk/layout';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatBadgeModule } from '@angular/material/badge';
import { CartService } from './services/cart.service';
import {MatMenuModule} from '@angular/material/menu';
import { CartItem } from './models/cart-item';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatSidenavModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatTabsModule,
    RouterModule,
    MatBadgeModule,
    MatMenuModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit, AfterViewInit {
  title = 'webshop';
  links = [
    { url: '/home', title: 'Home' },
    { url: '/products', title: 'Products' },
    { url: '/about-us', title: 'About Us' },
  ];
  activeLink = '';
  cartItems: CartItem[] = [];
  cartItemsCount: number = 0;

  mobileQuery: MediaQueryList;
  private _mobileQueryListener: () => void;

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private router: Router,
    private cartService: CartService,
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }
  ngAfterViewInit(): void {
    this.changeDetectorRef.detectChanges();
  }
  ngOnInit(): void {
    this.cartService.cartSub.subscribe(cartItemAdded => {
      this.getCartItems();
      this.getCartItemsCount();
    })

    setTimeout(() => {
      this.activeLink = this.router.url;
    }, 100);
    
    this.getCartItems();
    this.getCartItemsCount();
  }

  public getCartItemsCount(): void {
    this.cartItemsCount = this.cartService.getCartItemCount();
  }

  public getCartItems(): void {
    this.cartItems = this.cartService.getCartItems();
  }

  public getCartItemPrice(item: CartItem): number {
    return item.amount*item.price;
  }

  public openCartItemDetails(productId: number) {
    this.router.navigateByUrl(`/product-details/${productId}`);
  }

  public navigateToCartPage() {
    this.router.navigateByUrl('/cart');
  }
  
  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
}
