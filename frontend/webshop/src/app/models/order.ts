import { CartItem } from "./cart-item";
import { Person } from "./person";

export class Order {
    id: number;
    createdAt: Date;
    payId: string;
    payerId: string;
    sum: number;
    person: Person;
    items: CartItem[];
  
    constructor(
      id: number,
      createdAt: Date,
      sum: number,
      person: Person,
      payId: string,
      payerId: string,
      items: CartItem[]
    ) {
      this.id = id;
      this.createdAt = createdAt;
      this.payId = payId;
      this.payerId = payerId;
      this.items = items;
      this.sum = sum;
      this.person = person;
    }
  }
  