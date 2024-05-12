import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { jwtDecode } from 'jwt-decode';
import { Person } from '../models/person';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private oAuthService: OAuthService) {}

  public getUserRoles(): string[] {
    if (this.isAuthenticated()) {
      const token: any = jwtDecode(this.oAuthService.getAccessToken());
      console.log(token);
      const userRoles: string[] = token.realm_access.roles ?? '';
      return userRoles;
    } else {
      return [];
    }
  }

  public isAuthenticated(): boolean {
    return this.oAuthService.hasValidAccessToken();
  }

  public getUserName(): string {
    if (this.isAuthenticated()) {
      const token: any = jwtDecode(this.oAuthService.getAccessToken());
      const name: string = token.given_name ? token.given_name : 'User';
      return name;
    }

    return '';
  }

  public getUserId(): string {
    if (this.isAuthenticated()) {
      const token: any = jwtDecode(this.oAuthService.getAccessToken());
      const id: string = token.sub;
      return id;
    }
    return '';
  }

  public getUserData(): Person {
    let person: Person = {
      userId: '',
      address: '',
      city: '',
      country: '',
      email: '',
      firstName: '',
      lastName: '',
      phoneNumber: '',
      zipCode: 0,
    };
    const token: any = jwtDecode(this.oAuthService.getAccessToken());
    person.firstName = token.given_name ?? '';
    person.lastName = token.family_name ?? '';
    person.email = token.email ?? '';

    return person;
  }
}
