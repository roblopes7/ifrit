import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtService: JwtHelperService = new JwtHelperService()
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  isAuthenticated(): boolean {
    // Verifica se estamos no ambiente do navegador
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('token');
      if (token != null) {
        return !this.jwtService.isTokenExpired(token);
      }
    }
    // Se não estiver no navegador, retorna false
    return false;
  }

  logout() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.clear()
      }
    }
}
