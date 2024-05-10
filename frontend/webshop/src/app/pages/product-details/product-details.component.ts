import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { FormsModule, } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CartItem } from '../../models/cart-item';
import { CartService } from '../../services/cart.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [
    MatFormFieldModule,
    FormsModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.scss',
})
export class ProductDetailsComponent implements OnInit {
  productId!: number;
  product!: Product;
  isLoaded: boolean = false;

  amounts: number[] = Array.from({ length: 10 }, (_, i) => i + 1);
  selectedAmount: number = 1;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private cartService: CartService,
    private _snackBar: MatSnackBar,
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.productId = params['id'];

      this.productService
        .getProductById(this.productId)
        .subscribe((product) => {
          this.product = product;
          this.isLoaded = true;
        });
    });
  }

  public addProductToCart() {
    const cartItem = new CartItem(
      this.product.id,
      this.product.name,
      this.product.price,
      this.selectedAmount
    );

    this.cartService.addToCart(cartItem);
  }

  public openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {duration: 3000});
  }
}
