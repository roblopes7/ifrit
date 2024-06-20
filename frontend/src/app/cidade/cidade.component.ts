import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Observable } from 'rxjs';

import { Cidade } from './cidade';
import { CidadeService } from './service/cidade.service';

@Component({
  selector: 'app-cidade',
  standalone: true,
  imports: [MatToolbarModule, MatTableModule, MatCardModule, MatProgressSpinnerModule],
  templateUrl: './cidade.component.html',
  styleUrl: './cidade.component.scss'
})
export class CidadeComponent {

  cidades: Observable<Cidade[]>;
  displayedColumns = ['id', 'nome', 'uf', 'pais']

  constructor(private cidadeService: CidadeService) {
    this.cidades = cidadeService.list()
  }
}
