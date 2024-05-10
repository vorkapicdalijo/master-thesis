export class CartItem {
    id: number;
    name: string;
    price: number;
    amount: number;
  
    constructor(
      id: number,
      name: string,
      price: number,
      amount: number
    ) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.amount = amount;
    }
  }
  