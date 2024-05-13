import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import {
  ActivatedRoute,
  Router,
  RouterModule,
  RouterOutlet,
} from '@angular/router';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
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
import { PaymentService } from './services/payment.service';
import { PurchaseDialogComponent } from './dialogs/purchase-dialog/purchase-dialog.component';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from './services/auth.service';
import { OAuthEvent, OAuthService } from 'angular-oauth2-oidc';
import { environment } from '../environment/environment';

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
    MatMenuModule,
    MatDividerModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit, AfterViewInit {
  @ViewChild('sidenav') sidenav: MatSidenav;

  title = 'webshop';
  links = [
    { url: '', title: 'Home' },
    { url: '/products', title: 'Products' },
    { url: '/about-us', title: 'About Us' },
  ];
  activeLink = '';
  cartItems: CartItem[] = [];
  cartItemsCount: number = 0;
  userName: string = "";

  isAdmin: boolean = false;
  isAuthenticated: boolean = false;

  mobileQuery: MediaQueryList;
  private _mobileQueryListener: () => void;

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private router: Router,
    private cartService: CartService,
    private route: ActivatedRoute,
    private paymentService: PaymentService,    
    private authService: AuthService,
    private oAuthService: OAuthService,
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }
  ngAfterViewInit(): void {
    this.changeDetectorRef.detectChanges();
  }
  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthenticated();

    this.isAdmin = this.authService.getUserRoles().includes('admin');
    this.userName = this.authService.getUserName();

    this.oAuthService.events.subscribe(({ type }: OAuthEvent) => {
      this.isAuthenticated = this.authService.isAuthenticated();
      this.isAdmin = this.authService.getUserRoles().includes('admin');
      this.userName = this.authService.getUserName();

      this.cartService.relocateCartToUser();
      this.getCartItems();
      this.getCartItemsCount();
    });
    

    this.cartService.cartSub.subscribe(cartItemAdded => {
      this.getCartItems();
      this.getCartItemsCount();
    });

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
  
  public navigateToOrders() {
    this.router.navigateByUrl('/orders');
  }

  public login() {
    this.oAuthService.initLoginFlow();
  }

  public logout() {
    this.oAuthService.logOut();
  }

  public navigateToKeycloakConsole() {
    window.open(environment.keycloakUrl, "_blank");
  }

  closeSidenav() {
    this.sidenav.close();
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
}
