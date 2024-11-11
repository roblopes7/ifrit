
import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterModule, RouterOutlet } from '@angular/router';

import { CidadeComponent } from './pages/cidade/cidade.component';
import { AuthService } from './pages/login/services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MatToolbarModule, CidadeComponent, RouterModule,
    MatButtonModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'ifrit-web-app';

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private toast: ToastrService
  ) {}

  logout() {
    this.authService.logout();
    this.toast.success('Logout com sucesso', 'Logout', {timeOut: 7000})
    this.router.navigate(['login']);
  }
}
