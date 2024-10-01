import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map } from 'rxjs/operators';

import { DataTableComponent } from '../../components/data-table/data-table.component';
import { TableColumn } from '../../components/data-table/models/tableColumn';
import { DataTableResponseData } from '../../models/DataTableResponse';
import { Cidade } from './cidade';
import { CidadeService } from './services/cidade.service';

@Component({
  selector: 'app-cidade',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatProgressSpinnerModule, DataTableComponent],
  templateUrl: './cidade.component.html',
  styleUrls: ['./cidade.component.scss']
})
export class CidadeComponent implements OnInit {
  cidades: DataTableResponseData<Cidade> = {
    content: [],
    totalElements: 0,
    empty: true,
    first: true,
    size: 0,
    number: 0,
    numberOfElements: 0,
    totalPages: 0
  };

  colunas: TableColumn[] = [
    { key: 'id', header: 'Código IBGE' },
    { key: 'nome', header: 'Nome' },
    { key: 'uf', header: 'UF' },
    { key: 'pais', header: 'País' }
  ];

  constructor(private cidadeService: CidadeService) {}

  ngOnInit(): void {
    this.cidadeService.list().pipe(
      map((resposta: DataTableResponseData<Cidade>) => resposta)
    ).subscribe(data => {
      this.cidades = data;
    });
  }
}
