import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { MatButtonModule } from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { Product } from '../../models/product';
import { MatRippleModule } from '@angular/material/core';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { SizePrice } from '../../models/size-price';
import { AuthService } from '../../services/auth.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ProductFormDialogComponent } from '../../dialogs/product-form-dialog/product-form-dialog.component';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatRippleModule, MatIconModule, MatDialogModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent implements OnInit {

  isAdmin: boolean = false;
  products: Product[] = [];

  constructor(private productService: ProductService, private router: Router, private authService: AuthService, public dialog: MatDialog){}
  
  ngOnInit(): void {
    this.isAdmin = this.authService.getUserRoles().includes('admin');
    this.productService.getProducts()
      .subscribe(res => {
        this.products = res;
      })
  }

  public openProductDetails(productId: number) {
    this.router.navigateByUrl(`product-details/${productId}`);
  }

  public getLowestSizePrice(sizePrices: SizePrice[]) {
    if (sizePrices) {
      return sizePrices.reduce((minObj, currentObj) => {
        if (!minObj || currentObj['price'] < minObj['price']) {
          return currentObj;
        }
        return minObj;
      });
    }
    return null;
  }

  addProduct() {
    const dialogRef = this.dialog.open(ProductFormDialogComponent, {
      data: {
        isEdit: false
      },
      position: {
        top: '100px'
      },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(res => {
      console.log(res);
    });
  }

}
