import { Routes } from '@angular/router';

import { CidadeComponent } from './cidade/cidade.component';

export const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'cidades'},
  {
    path: 'cidades',
    loadComponent: () => import('./cidade/cidade.component').then(m => m.CidadeComponent)
  }
];
