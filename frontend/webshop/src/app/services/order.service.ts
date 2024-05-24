import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order } from '../models/order';
import { AuthService } from './auth.service';
import { environment } from '../../environment/environment';
import { Observable } from 'rxjs';

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

  public checkProductAvailability(productId: number, amount: number): Observable<boolean> {
    return this.http.post<boolean>(environment.baseUrl + 'api/inventory/check',
      {
        productId: productId,
        amount: amount
      }
    );
  }
}
