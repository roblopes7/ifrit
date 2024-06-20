import { provideHttpClient, withXsrfConfiguration } from '@angular/common/http';
import { ApplicationConfig } from '@angular/core';
import { provideClientHydration } from '@angular/platform-browser';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
    provideClientHydration(),
    provideAnimationsAsync(),
    [provideHttpClient(withXsrfConfiguration({
    cookieName: 'TOKEN', // default is 'XSRF-TOKEN'
    headerName: 'X-TOKEN' // default is 'X-XSRF-TOKEN'
  }))]]
};
