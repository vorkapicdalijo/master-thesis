import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BreakpointObserver} from '@angular/cdk/layout';
import {StepperOrientation, MatStepperModule} from '@angular/material/stepper';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {AsyncPipe} from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { CartService } from '../../services/cart.service';
import { CartItem } from '../../models/cart-item';
import { Router } from '@angular/router';
import { MatMenuModule } from '@angular/material/menu';

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

  cartItems: CartItem[] = [];

  constructor(
    private fb: FormBuilder,
    breakpointObserver: BreakpointObserver,
    private cartService: CartService,
    private router: Router
  ) {
    this.stepperOrientation = breakpointObserver
      .observe('(min-width: 750px)')
      .pipe(map(({matches}) => (matches ? 'horizontal' : 'vertical')));
  }

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
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

  public savePersonInfo() {
    console.log(this.personalInfoFormGroup);
  }

  

}
