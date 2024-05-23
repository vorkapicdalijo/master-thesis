import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
import {MatExpansionModule} from '@angular/material/expansion';
import { Review } from '../../models/review';
import { DatePipe } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { ProductFormDialogComponent } from '../../dialogs/product-form-dialog/product-form-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { PurchaseDialogComponent } from '../../dialogs/purchase-dialog/purchase-dialog.component';

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [
    MatFormFieldModule,
    FormsModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatExpansionModule,
    DatePipe
  ],
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.scss',
})
export class ProductDetailsComponent implements OnInit {
  productId!: number;
  product!: Product;
  isLoaded: boolean = false;
  panelOpenState = false;

  amounts: number[] = Array.from({ length: 10 }, (_, i) => i + 1);
  starArray: number[] = Array.from({ length: 5 }, (_, i) => i + 1);
  selectedAmount: number = 1;

  isAdmin: boolean = false;

  // reviews: Review[] = [
  //   {id:1, productId: 52, userId: '1', userName: 'Marko', comment: 'Loving the product', rating: 4.5, createdAt: new Date()},
  //   {id:1, productId: 52, userId: '1', userName: 'Marko', comment: 'Loving the product', rating: 4.5, createdAt: new Date()},
  //   {id:1, productId: 52, userId: '1', userName: 'Marko', comment: 'Loving the product', rating: 4.5, createdAt: new Date()}
  // ]

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private cartService: CartService,
    private _snackBar: MatSnackBar,
    private authService: AuthService,
    public dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.getUserRoles().includes('admin');

    this.route.params.subscribe((params) => {
      this.productId = params['id'];

      this.getProduct();
    });
  }

  public getProduct() {
    this.isLoaded = false;
    this.productService
    .getProductById(this.productId)
      .subscribe((product) => {
        this.product = product;
        this.isLoaded = true;
      });
  }

  public addProductToCart() {
    const cartItem = new CartItem(
      this.product.productId,
      this.product.name,
      this.product.productId,
      this.selectedAmount,
      null!
    );

    this.cartService.addToCart(cartItem);
  }

  public openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {duration: 3000});
  }

  showRatingIcon(rating: number, index:number) {
    if (rating >= index + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }

  editProduct() {
    const dialogRef = this.dialog.open(ProductFormDialogComponent, {
      data: {
        isEdit: true,
        product: this.product
      },
      position: {
        top: '100px'
      },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(res => {
      this.openSnackBar('Fragrance updated!', 'Dismiss');
      this.getProduct();
    });
  }

  deleteProduct() {
    let title = "Fragrance deleted!"
    let content = `A fragrance with ID ${this.product.productId} has been deleted.`
    this.productService.deleteProduct(this.product.productId)
      .subscribe(res => {
        const dialogRef = this.dialog.open(PurchaseDialogComponent, {
          width: '250px',
          enterAnimationDuration:'1500ms',
          exitAnimationDuration: '800ms',
          data: {
            title: title,
            content: content,
          },
          position: {
            top: '100px'
          },
        });
        this.router.navigateByUrl('/products');
      });
  }
  
}
