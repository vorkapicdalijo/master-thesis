import { Component, OnInit, PLATFORM_ID, ViewChild, inject } from '@angular/core';
import {FormBuilder, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BreakpointObserver} from '@angular/cdk/layout';
import {StepperOrientation, MatStepperModule, MatStepper} from '@angular/material/stepper';
import {Observable} from 'rxjs';
import {first, map, take} from 'rxjs/operators';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {AsyncPipe, isPlatformBrowser} from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { CartService } from '../../services/cart.service';
import { CartItem } from '../../models/cart-item';
import { ActivatedRoute, Router } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';
import { PaymentService, PaypalResponse } from '../../services/payment.service';
import { Order } from '../../models/order';
import { Person } from '../../models/person';
import { AuthService } from '../../services/auth.service';
import { OrderService } from '../../services/order.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PurchaseDialogComponent } from '../../dialogs/purchase-dialog/purchase-dialog.component';

@Component({
  selector: 'app-purchase',
  standalone: true,
  imports: [
    MatStepperModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    AsyncPipe,
    MatIconModule,
    MatMenuModule,
    MatDialogModule,
  ],
  templateUrl: './purchase.component.html',
  styleUrl: './purchase.component.scss'
})
export class PurchaseComponent implements OnInit {
  @ViewChild('stepper') stepper!: MatStepper;

  personalInfoFormGroup = this.fb.group({
    firstName: ['', Validators.required,],
    lastName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: ['', [Validators.required, Validators.minLength(7), Validators.maxLength(12), Validators.pattern('[- +()0-9]+')]],
  });
  addressFormGroup = this.fb.group({
    address: ['', Validators.required],
    city: ['', Validators.required],
    zipCode: ['', Validators.required],
    country: ['', Validators.required],
  });
  thirdFormGroup = this.fb.group({
    thirdCtrl: ['', Validators.required],
  });
  stepperOrientation: Observable<StepperOrientation>;
  currentStep = 0;

  orderDetails!: Order;
  cartItems: CartItem[] = [];
  paypalResponse!: PaypalResponse;
  token!: string;
  payerId!: string;
  payId!: string;

  private readonly platformId = inject(PLATFORM_ID);

  constructor(
    private fb: FormBuilder,
    breakpointObserver: BreakpointObserver,
    private cartService: CartService,
    private router: Router,
    private paymentService: PaymentService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private orderService: OrderService,
    public dialog: MatDialog,
  ) {
    this.stepperOrientation = breakpointObserver
      .observe('(min-width: 750px)')
      .pipe(map(({matches}) => (matches ? 'horizontal' : 'vertical')));
  }

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
    this.token = (this.route.snapshot.queryParams['token']);
    this.payerId = (this.route.snapshot.queryParams['PayerID']);
    if(this.router.url.includes('capture')) {
      if (this.token) {
        this.paymentService.completePayment(this.token)
          .subscribe(res => {
            this.saveOrderDetails();
            this.cartService.clearCart();
            this.openDialog('1500ms', '800ms', 'Order canceled !');
            this.router.navigateByUrl('');
          });
      }
    }
    if(this.router.url.includes('cancel')) {
      this.openDialog('1500ms', '800ms', 'Order canceled !');
      this.router.navigateByUrl('/cart');
    }
    this.fillPersonForm();
  }

  public fillPersonForm() {
    let authPerson: Person = this.authService.getUserData();

    this.personalInfoFormGroup.get('firstName')?.setValue(authPerson.firstName);
    this.personalInfoFormGroup.get('lastName')?.setValue(authPerson.lastName);
    this.personalInfoFormGroup.get('email')?.setValue(authPerson.email);
  }

  public openCartItemDetails(productId: number) {
    this.router.navigateByUrl(`/product-details/${productId}`);
  }

  public getCartItemPrice(item: CartItem): number {
    return item.amount*item.price;
  }

  public navigateToCartPage() {
    this.router.navigateByUrl('/cart');
  }

  public calculateTotalPrice(): number {
    let total = 0;
    this.cartItems.forEach(item => {
      total = total + (item.amount*item.price);
    });

    return total;
  }

  public submitPurchaseData() {
    let buyerInfo: Person = new Person(
      this.authService.getUserId(),
      this.personalInfoFormGroup.get('firstName')?.value!,
      this.personalInfoFormGroup.get('lastName')?.value!,
      this.personalInfoFormGroup.get('email')?.value!,
      this.personalInfoFormGroup.get('phoneNumber')?.value!,
      this.addressFormGroup.get('address')?.value!,
      this.addressFormGroup.get('city')?.value!,
      parseInt(this.addressFormGroup.get('zipCode')?.value!),
      this.addressFormGroup.get('country')?.value!,
    );

    let orderDetails: Order = new Order(
      null!,
      new Date(),
      this.calculateTotalPrice(),
      buyerInfo,
      this.payId,
      this.payerId,
      this.cartItems
    );

    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('orderDetails', JSON.stringify(orderDetails));
    }
  }

  public saveOrderDetails() {
    if (isPlatformBrowser(this.platformId)) {
      this.orderDetails = JSON.parse(localStorage.getItem('orderDetails')!) ?? {}
      this.payId = JSON.parse(localStorage.getItem('payId')!) ?? ''
      this.orderDetails.payId = this.payId;
      this.orderDetails.payerId = this.payerId;
      
      this.orderService.sendOrderDetails(this.orderDetails)
        .subscribe(res => {
        })
    }
  }

  public sendPaymentOrder() {

    this.paymentService.sendPaymentOrder(this.calculateTotalPrice())
      .pipe(take(1))
      .subscribe(res => {
        if(isPlatformBrowser(this.platformId)) {
          localStorage.setItem('payId', JSON.stringify(res.payId))
        }
        window.location.href = res.redirectUrl
      })
  }

  public openDialog(enterAnimationDuration: string, exitAnimationDuration: string, title: string): void {
    const dialogRef = this.dialog.open(PurchaseDialogComponent, {
      width: '250px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        title: title
      },
      position: {
        top: '100px'
      },
    });
  }

  

}
