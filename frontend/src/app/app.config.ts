import { routes } from './app.routes';
import {
  HTTP_INTERCEPTORS,
  provideHttpClient,
  withInterceptors,
  withXsrfConfiguration,
} from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideClientHydration } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter } from '@angular/router';
import { JwtModule } from '@auth0/angular-jwt';
import { ToastrModule } from 'ngx-toastr';
import { authInterceptor } from './interceptors/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideClientHydration(),
    provideAnimationsAsync(),
    provideAnimations(),
    [
      provideHttpClient(
        withXsrfConfiguration({
          cookieName: 'TOKEN', // default is 'XSRF-TOKEN'
          headerName: 'X-TOKEN', // default is 'X-XSRF-TOKEN'
        }),
        withInterceptors([authInterceptor])
      ),
      importProvidersFrom(
        ToastrModule.forRoot({
          timeOut: 5000,
          positionClass: 'toast-top-right',
          preventDuplicates: true,
          progressBar: true,
        }),
        JwtModule.forRoot({
          config: {
            tokenGetter: () => localStorage.getItem('token'),
          },
        })
      ),
    ],
  ],
};
