export class CartItem {
    productId: number;
    name: string;
    price: number;
    amount: number;
  
    constructor(
      productId: number,
      name: string,
      price: number,
      amount: number
    ) {
      this.productId = productId;
      this.name = name;
      this.price = price;
      this.amount = amount;
    }
  }
  