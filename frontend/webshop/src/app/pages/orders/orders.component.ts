import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss'
})
export class OrdersComponent implements OnInit {

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.orderService.getOrdersByUserId()
      .subscribe(orders => {
        console.log(orders);
      })
  }

}
