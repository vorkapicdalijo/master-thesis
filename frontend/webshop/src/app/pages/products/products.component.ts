import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { MatButtonModule } from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { Product } from '../../models/product';
import { MatRippleModule } from '@angular/material/core';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatRippleModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];

  constructor(private productService: ProductService){}
  
  ngOnInit(): void {
    this.productService.getProducts()
      .subscribe(res => {
        this.products = res;
      })
  }


}
