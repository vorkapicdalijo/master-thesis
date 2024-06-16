import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatButtonModule, RouterModule,],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  MALE_CATEGORY_ID = 1;
  FEMALE_CATEGORY_ID = 2;
  UNISEX_CATEGORY_ID = 3;

  constructor() {
  }
}
