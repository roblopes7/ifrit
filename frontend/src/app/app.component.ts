import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterModule, RouterOutlet } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { AuthService } from './pages/login/services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatToolbarModule,
    RouterModule,
    MatButtonModule,
    CommonModule
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ifrit-web-app';
  showToolbar = true;

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private toast: ToastrService
  ) {
    this.router.events.subscribe(() => {
      this.showToolbar = this.router.url !== '/login';
    });
  }

  logout() {
    this.authService.logout();
    this.toast.success('Logout com sucesso', 'Logout', { timeOut: 7000 });
    this.router.navigate(['login']);
  }
}
