import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatButtonModule, RouterModule,],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  MALE_CATEGORY_ID = 1;
  FEMALE_CATEGORY_ID = 2;
  UNISEX_CATEGORY_ID = 3;

  constructor(private cartService: CartService) {
  }
  ngOnInit(): void {
    this.cartService.cartSub.next(true);
  }
}
