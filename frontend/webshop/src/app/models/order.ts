import { CartItem } from "./cart-item";
import { Person } from "./person";
import { Review } from "./review";

export class Order {
    id: number;
    createdAt: Date;
    payId: string;
    payerId: string;
    sum: number;
    person: Person;
    items: CartItem[];
    review: Review;
  
    constructor(
      id: number,
      createdAt: Date,
      sum: number,
      person: Person,
      payId: string,
      payerId: string,
      items: CartItem[],
      review: Review,
    ) {
      this.id = id;
      this.createdAt = createdAt;
      this.payId = payId;
      this.payerId = payerId;
      this.items = items;
      this.sum = sum;
      this.person = person;
      this.review = review;
    }
  }
  