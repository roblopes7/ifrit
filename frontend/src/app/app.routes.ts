import { Routes } from '@angular/router';

import { authGuard } from './auth/auth.guard';
import { ToolbarComponent } from './components/toolbar/toolbar.component';

export const routes: Routes = [
//  { path: '', pathMatch: 'full', redirectTo: 'login' },
  {
    path: 'login',
    loadComponent: () =>
      import('./pages/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: '',
    data: { title: 'Home' },
    component: ToolbarComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'cidades',
        data: { title: 'Cidades' },
        loadComponent: () =>
          import('./pages/cidade/cidade.component').then(
            (m) => m.CidadeComponent
          ),
      },
      {
        path: 'usuarios',
        data: { title: 'Usuários' },
        loadComponent: () =>
          import('./pages/usuarios/usuarios.component').then(
            (m) => m.UsuariosComponent
          ),
      },
    ],
  },
];
