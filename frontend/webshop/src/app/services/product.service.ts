import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient, private oauthService: OAuthService) {}

  public getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>('http://localhost:8083/api/products');
  }

  public getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`http://localhost:8083/api/products/${id}`);
  }
}
