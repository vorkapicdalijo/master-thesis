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
  reviews?: Review[];
  averageRatingAndCount?: AverageRatingAndCount;
  amount?: number;
}
