export class Product {
  id: number;
  name: string;
  price: number;
  size: number;
  categoryId: number;
  typeId: number;
  brandId: number;
  description: string;

  constructor(
    id: number,
    name: string,
    price: number,
    categoryId: number,
    typeId: number,
    brandId: number,
    size: number,
    description: string
  ) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
    this.size = size;
    this.typeId = typeId;
    this.brandId = brandId;
    this.description = description;
  }
}
