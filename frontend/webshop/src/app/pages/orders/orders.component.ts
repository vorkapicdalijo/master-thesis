import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import {MatTableModule} from '@angular/material/table';
import { Order } from '../../models/order';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatListModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.scss',
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class OrdersComponent implements OnInit {
  columnsToDisplay: string[] = ['id', 'createdAt', 'payId', 'payerId', 'sum'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement!: Order | null;
  orders: Order[] = [];
  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.orderService.getOrdersByUserId()
      .subscribe(orders => {
        this.orders = orders;
      })
  }

}
