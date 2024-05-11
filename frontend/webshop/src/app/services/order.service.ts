import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order } from '../models/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  public sendOrderDetails(orderDetails: Order) {
    return this.http.post<any>('http://localhost:8083/api/order', 
      orderDetails
    );
  }
}
