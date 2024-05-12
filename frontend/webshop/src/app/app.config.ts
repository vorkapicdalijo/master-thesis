import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideAnimations } from '@angular/platform-browser/animations';
import { AuthConfig, OAuthService, provideOAuthClient } from 'angular-oauth2-oidc';
import { authInterceptor } from './interceptors/auth.interceptor';

export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8181/realms/webshop',
  tokenEndpoint: 'http://localhost:8181/realms/webshop/protocol/openid-connect/token',
  redirectUri: 'http://localhost:4200',
  clientId: 'webshop-frontend',
  responseType: 'code',
  scope: 'openid profile',
  showDebugInformation: true,
};

function initializeOAuth(oauthService: OAuthService): Promise<void> {
  return new Promise((resolve) => {
    oauthService.configure(authCodeFlowConfig);
    oauthService.setupAutomaticSilentRefresh();
    oauthService.loadDiscoveryDocumentAndLogin()
      .then(() => resolve());
  });
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideClientHydration(),
    provideHttpClient(withFetch(), withInterceptors([
      authInterceptor
    ])),
    provideAnimationsAsync(),
    provideOAuthClient(),
    {
      provide: APP_INITIALIZER,
      useFactory: (oauthService: OAuthService) => {
        return () => {
          initializeOAuth(oauthService);
        }
      },
      multi: true,
      deps: [
        OAuthService
      ]
    },
  ]
};
