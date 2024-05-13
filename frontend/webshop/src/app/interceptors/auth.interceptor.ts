import { HttpInterceptorFn, } from "@angular/common/http";
import { inject } from "@angular/core";
import { OAuthService } from "angular-oauth2-oidc";


export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const oAuthService = inject(OAuthService);
  const authToken = oAuthService.getAccessToken();

  // Clone the request and add the authorization header
  if (oAuthService.hasValidAccessToken()) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    });

    // Pass the cloned request with the updated header to the next handler
    return next(authReq);
  }

  return next(req);

};