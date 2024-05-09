import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLinkActive, RouterModule, RouterOutlet } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MediaMatcher } from '@angular/cdk/layout';
import { MatButtonModule } from '@angular/material/button';
import {MatTabsModule} from '@angular/material/tabs';
import { ThemePalette } from '@angular/material/core';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, 
    MatSidenavModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatTabsModule,
    RouterModule,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  title = 'webshop';
  links = [
    {url: '/home', title: 'Home'},
    {url: '/products', title: 'Products'},
    {url: '/about-us', title: 'About Us'},
  ]
  activeLink = "";

  mobileQuery: MediaQueryList;
  private _mobileQueryListener: () => void;

  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher, private router: Router) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }
  ngOnInit(): void {
    setTimeout(() => {
      this.activeLink = this.router.url;
    }, 100);

  }

  isLinkActive(rla: RouterLinkActive) {
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }
}
