import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map } from 'rxjs/operators';

import { PageParams } from '../../components/data-table/models/pageParams';
import { TableColumn, TableData } from '../../components/data-table/models/tableColumn';
import { DataTableResponseData } from '../../models/DataTableResponse';
import { Cidade } from './cidade';
import { CidadeService } from './services/cidade.service';

@Component({
  selector: 'app-cidade',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatSortModule, MatIconModule,
     MatProgressSpinnerModule, MatPaginatorModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './cidade.component.html',
  styleUrls: ['./cidade.component.scss']
})
export class CidadeComponent implements OnInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;

  @ViewChild('input') inputElement!: ElementRef;

  isLoading = false;

  // Estado da página e tamanho da página
  pageIndex = 0;
  pageSize = 25;

  // Variável para armazenar os dados
  dataSource: MatTableDataSource<TableData>;
  displayedColumns: string[] = ['id', 'nome', 'uf', 'pais'];

  totalElements = 0;  // Total de elementos para o paginador

  constructor(private cidadeService: CidadeService) {
    this.dataSource = new MatTableDataSource<TableData>([]);
  }

  ngOnInit(): void {
    this.loadCidades();
  }

  loadCidades(pageParams: Partial<PageParams> = {}): void {
    this.cidadeService.list('', pageParams).subscribe(data => {
      this.dataSource.data = data.content;
      this.totalElements = data.totalElements;  // Atualiza o total de elementos
    });
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;

    // Chama a função para carregar os dados da nova página
    this.loadCidades({
      page: this.pageIndex,
      linesPerPage: this.pageSize
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  consultar() {
    const pageParams: Partial<PageParams> = {};
    const filter = this.inputElement.nativeElement.value;
    this.cidadeService.list(filter, pageParams).subscribe(data => {
      this.dataSource.data = data.content;
      this.totalElements = data.totalElements;  // Atualiza o total de elementos
    });
  }

  consultarIBGE() {
    this.isLoading = true;
    this.cidadeService.updateIbge().subscribe({
      next: () => {
        this.loadCidades();
      },
      error: (err) => {
        console.error('Erro ao consultar IBGE', err);
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}
