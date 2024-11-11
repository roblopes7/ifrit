import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { API_CONFIG } from '../../../config/api.config';
import { Login } from '../models/Login';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private readonly API: string =  "/auth"

  constructor(private http: HttpClient) { }

  login(login: Login) {
    return this.http.post(`${API_CONFIG.baseUrl}${this.API}/login`, login, {
      observe: 'response',
      responseType: 'text'
    })
  }

  successfullLogin (authToken: string){
    localStorage.setItem('token', authToken)
  }
}
