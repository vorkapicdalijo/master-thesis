import { Review } from "./review";

export class CartItem {
    productId: number;
    name: string;
    price: number;
    amount: number;
    review: Review;
  
    constructor(
      productId: number,
      name: string,
      price: number,
      amount: number,
      review: Review
    ) {
      this.productId = productId;
      this.name = name;
      this.price = price;
      this.amount = amount;
      this.review = review;
    }
  }
  