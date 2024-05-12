import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order } from '../models/order';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  public sendOrderDetails(orderDetails: Order) {
    return this.http.post<any>('http://localhost:8083/api/order', 
      orderDetails
    );
  }

  public getOrdersByUserId() {
    return this.http.get<Order[]>(`http://localhost:8083/api/order/${this.authService.getUserId()}`);
  }
}
