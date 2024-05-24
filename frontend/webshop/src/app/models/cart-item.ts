import { Review } from "./review";

export class CartItem {
    productId: number;
    name: string;
    brand: string;
    type: string;
    size: number;
    price: number;
    amount: number;
    review: Review;
    imageUrl: string;
    isAvailable?: boolean;

  }
  