import { AverageRatingAndCount } from "./average-rating-and-count";
import { Review } from "./review";

export class Product {
  id: number;
  name: string;
  price: number;
  size: number;
  categoryId: number;
  typeId: number;
  brandId: number;
  description: string;
  reviews: Review[];
  averageRatingAndCount: AverageRatingAndCount;

  constructor(
    id: number,
    name: string,
    price: number,
    categoryId: number,
    typeId: number,
    brandId: number,
    size: number,
    description: string,
    reviews: Review[],
    averageRatingAndCount: AverageRatingAndCount
  ) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.categoryId = categoryId;
    this.size = size;
    this.typeId = typeId;
    this.brandId = brandId;
    this.description = description;
    this.averageRatingAndCount = averageRatingAndCount;
    this.reviews = reviews;
  }
}
