import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export interface PaypalResponse {
  token?: string,
  PayerID?: string,
  payID?: string,
}

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  paymentSub: Subject<string> = new Subject();
  flag: boolean = false;
  paypalResponse!: PaypalResponse;

  constructor(private http: HttpClient) { }

  public sendPaymentOrder(sum: number) {
    return this.http.post<any>('http://localhost:8083/api/payment/init', 
    {},
    {
      params: {
        sum: sum
      }
    });
  }

  public completePayment(paymentToken: string) {
    return this.http.post<any>('http://localhost:8083/api/payment/capture', 
    {},
    {
      params: {
        token: paymentToken
      }
    });
  }
}
