import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { MatTableModule } from '@angular/material/table';
import { Order } from '../../models/order';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { MatListModule } from '@angular/material/list';
import { ReviewService } from '../../services/review.service';
import { Review, ReviewId } from '../../models/review';
import { AuthService } from '../../services/auth.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    DatePipe,
  ],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss',
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      ),
    ]),
  ],
})
export class OrdersComponent implements OnInit {
  columnsToDisplay: string[] = ['id', 'createdAt', 'payId', 'payerId', 'sum'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement!: Order | null;
  orders: Order[] = [];

  comment: string = '';
  rating: number = -1;
  ratingArray: number[] = Array.from({ length: 5 }, (_, i) => i + 1);

  isRatingProduct: boolean = false;
  currentRatingProductId: number = 0;
  currentRatingOrderId: number = 0;

  constructor(
    private orderService: OrderService,
    private reviewService: ReviewService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.orderService.getOrdersByUserId().subscribe((orders) => {
      this.orders = orders;
    });
  }

  showRatingIcon(index: number, rating: number) {
    if (rating >= index + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }

  onRatingClick(rating: number, productId: number) {
    this.rating = rating;
  }

  toggleProductRating(orderId: number, productId: number) {
    this.rating = -1;
    this.comment = '';
    this.isRatingProduct = true;
    if (this.isRatingProduct) {
      this.currentRatingOrderId = orderId;
      this.currentRatingProductId = productId;
    }
  }

  addRating(orderId: number, productId: number) {
    let reviewId: ReviewId = {
      productId: productId,
      userId: this.authService.getUserId(),
    };

    let review: Review = {
      reviewId: reviewId,
      userName: this.authService.getUserName(),
      createdAt: new Date(),
      rating: this.rating,
      comment: this.comment,
    };

    this.reviewService.addReview(review).subscribe((res: Review) => {
      if (res) {
        this.orders.map((order) => {
          if (order.id == orderId) {
            order.items = order.items.map((item) => {
              if (item.productId == productId) {
                item.review = res;
              }
              return item;
            });
          }
        });

        this.isRatingProduct = false;
      }
    });
  }

  changeReview(orderId: number, review: Review) {
    this.comment = review.comment;
    this.rating = review.rating;

    this.currentRatingOrderId = orderId;
    this.currentRatingProductId = review.reviewId.productId;
    this.isRatingProduct = true;
  }

  cancelRating() {
    this.isRatingProduct = false;
  }
}
