import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product';
import { OAuthService } from 'angular-oauth2-oidc';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient, private oauthService: OAuthService) {}

  public getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(environment.baseUrl+'api/products');
  }

  public getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(environment.baseUrl+`api/products/${id}`);
  }

  public addProduct(formData: FormData): Observable<any> {
    return this.http.post(environment.baseUrl+'api/products/add',
      formData
    );
  }

  public updateProduct(formData: FormData, productId: number): Observable<any> {
    return this.http.post(environment.baseUrl+`api/products/update/${productId}`,
      formData
    );
  }

  public deleteProduct(productId: number) {
    return this.http.delete(environment.baseUrl+`api/products/delete/${productId}`);
  }
}
