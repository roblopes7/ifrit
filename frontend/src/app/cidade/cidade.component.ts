import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map, Observable, of } from 'rxjs';

import { DataTableResponseData } from '../models/DataTableResponse';
import { Cidade } from './cidade';
import { CidadeService } from './service/cidade.service';

@Component({
  selector: 'app-cidade',
  standalone: true,
  imports: [MatToolbarModule, MatTableModule, MatCardModule, MatProgressSpinnerModule],
  templateUrl: './cidade.component.html',
  styleUrl: './cidade.component.scss'
})
export class CidadeComponent implements OnInit{

  cidades: Observable<Cidade[]> = of([]);
  displayedColumns = ['id', 'nome', 'uf', 'pais'];

  constructor(private cidadeService: CidadeService) {}

  ngOnInit(): void {
    this.cidades = this.cidadeService.list().pipe(
      map((resposta: DataTableResponseData) => resposta.content as Cidade[]))
  }
}
