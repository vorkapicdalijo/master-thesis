import { AverageRatingAndCount } from "./average-rating-and-count";
import { Brand } from "./brand";
import { Category } from "./category";
import { ProductNote } from "./product-note";
import { Review } from "./review";
import { SizePrice } from "./size-price";
import { Type } from "./type";

export class Product {
  productId: number;
  name: string;
  category: Category;
  type: Type;
  brand: Brand;
  description: string;
  imageUrl: string;
  sizePrices: SizePrice[];
  productNotes: ProductNote[];
  reviews: Review[];
  averageRatingAndCount: AverageRatingAndCount;

  constructor(
    productId: number,
    name: string,
    category: Category,
    type: Type,
    brand: Brand,
    description: string,
    imageUrl: string,
    sizePrices: SizePrice[],
    productNotes: ProductNote[],
    reviews: Review[],
    averageRatingAndCount: AverageRatingAndCount
  ) {
    this.productId = productId;
    this.name = name;
    this.category = category;
    this.type = type;
    this.brand = brand;
    this.description = description;
    this.imageUrl = imageUrl;
    this.sizePrices = sizePrices;
    this.productNotes = productNotes;
    this.averageRatingAndCount = averageRatingAndCount;
    this.reviews = reviews;
  }
}
