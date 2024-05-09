export class Product {
    id: number;
    name: string;
    price: number;
    categoryId: number;
    categoryName: string;

    constructor(id: number, name: string, price: number, categoryId: number, categoryName: string) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}