import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { response } from 'express';
import { ToastrModule, ToastrService } from 'ngx-toastr';

import { isStringValid } from '../../utils/functions/string-utils';
import { Login } from './models/Login';
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatButtonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    ToastrModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  login: Login = {
    login: '',
    senha: '',
  };

  constructor(
    private loginService: LoginService,
    private toast: ToastrService,
    private router: Router
  ) {}

  onLogin() {
    this.loginService.login(this.login).subscribe(response => {
      if(response.body == null) {
        this.toast.error('Token não encontrado')
        return;
      }
        this.loginService.successfullLogin(response.body);
        this.router.navigate([''])
    }, () => {
      this.toast.error('Usuário ou senha incorreto.');
    });
  }


  validarCampos() {
    return isStringValid(this.login.login) && isStringValid(this.login.senha);
  }
}
