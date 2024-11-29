import { CommonModule } from '@angular/common';
import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';

import { PageParams } from '../../components/data-table/models/pageParams';
import { TableData } from '../../components/data-table/models/tableColumn';
import { TelefonePipe } from '../../pipes/telefone-pipe';
import { AddEmpresaComponent } from './add/add.component';
import { EmpresaService } from './services/empresa.service';

@Component({
  selector: 'app-empresas',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatSortModule, MatIconModule,
    MatCheckboxModule, MatSlideToggleModule,
    MatProgressSpinnerModule, MatPaginatorModule, MatFormFieldModule,
    MatInputModule, MatButtonModule, ReactiveFormsModule, TelefonePipe],
  templateUrl: './empresas.component.html',
  styleUrls: ['./empresas.component.scss', '../../styles/list-styles.scss']
})
export class EmpresasComponent {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;

  @ViewChild('input') inputElement!: ElementRef;

  readonly dialog = inject(MatDialog);

  mostrarInativos = new FormControl(true);

  isLoading = false;

  pageIndex = 0;
  pageSize = 25;

  dataSource: MatTableDataSource<TableData>;
  displayedColumns: string[] = ['nomeFantasia', 'responsavel', 'cidade', 'telefone', 'celular'];

  totalElements = 0;

  constructor(private empresaService: EmpresaService) {
    this.dataSource = new MatTableDataSource<TableData>([]);
  }

  ngOnInit(): void {
    this.loadEmpresas();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
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
    this.empresaService.list(filter, pageParams).subscribe(data => {
      this.dataSource.data = data.content;
      this.totalElements = data.totalElements;
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;

    this.loadEmpresas({
      page: this.pageIndex,
      linesPerPage: this.pageSize
    });
  }

  loadEmpresas(pageParams: Partial<PageParams> = {}): void {
    this.empresaService.list('', pageParams).subscribe(data => {
      if(data) {
        this.dataSource.data = data.content;
        this.totalElements = data.totalElements;
      } else {
        this.dataSource.data = [];
        this.totalElements = 0;
      }
    });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddEmpresaComponent, {
      hasBackdrop: true,
      disableClose: false,
      autoFocus: true,
      width: '80%',
      height: 'auto',
      maxWidth: '1000px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadEmpresas();
      }
    });
  }
}
