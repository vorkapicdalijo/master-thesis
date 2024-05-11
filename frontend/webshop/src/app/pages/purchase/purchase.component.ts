import { Component, OnInit, ViewChild } from '@angular/core';
import {FormBuilder, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BreakpointObserver} from '@angular/cdk/layout';
import {StepperOrientation, MatStepperModule, MatStepper} from '@angular/material/stepper';
import {Observable} from 'rxjs';
import {first, map, take} from 'rxjs/operators';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {AsyncPipe} from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { CartService } from '../../services/cart.service';
import { CartItem } from '../../models/cart-item';
import { ActivatedRoute, Router } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';
import { PaymentService, PaypalResponse } from '../../services/payment.service';
import { Order } from '../../models/order';
import { Person } from '../../models/person';
import { AuthService } from '../../services/auth.service';

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

  cartItems: CartItem[] = [];
  paypalResponse!: PaypalResponse;
  isPaymentSuccess: boolean = false;
  isPaymentCanceled: boolean = false;
  finishedPayment: boolean = false;

  constructor(
    private fb: FormBuilder,
    breakpointObserver: BreakpointObserver,
    private cartService: CartService,
    private router: Router,
    private paymentService: PaymentService,
    private authService: AuthService,
    private route: ActivatedRoute,
  ) {
    this.stepperOrientation = breakpointObserver
      .observe('(min-width: 750px)')
      .pipe(map(({matches}) => (matches ? 'horizontal' : 'vertical')));
  }

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
    let token = (this.route.snapshot.queryParams['token']);

    if (token) {
      this.paymentService.completePayment(token)
        .subscribe(res => {
          this.currentStep = 3;
          this.isPaymentSuccess = true;
        });
    }
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
      this.authService.userId,
      this.personalInfoFormGroup.get('firstname')?.value!,
      this.personalInfoFormGroup.get('lastName')?.value!,
      this.personalInfoFormGroup.get('email')?.value!,
      this.personalInfoFormGroup.get('phoneNumber')?.value!,
      this.addressFormGroup.get('address')?.value!,
      this.addressFormGroup.get('city')?.value!,
      parseInt(this.addressFormGroup.get('zipCode')?.value!),
      this.addressFormGroup.get('country')?.value!,
    );

    // let purchaseInfo: Purchase = new Purchase(
    //   null,
    //   new Date(),
    //   this.calculateTotalPrice(),
    //   buyerInfo,
    //   this.paypalResponse.PayerID
    // )

    
  }

  public sendPaymentOrder() {
    var sum = 2;

    this.paymentService.sendPaymentOrder(sum)
      .pipe(take(1))
      .subscribe(res => {
        console.log(res);
        window.location.href = res.redirectUrl
      })
  }

  

}
