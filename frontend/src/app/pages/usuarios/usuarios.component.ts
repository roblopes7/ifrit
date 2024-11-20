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
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';

import { PageParams } from '../../components/data-table/models/pageParams';
import { TableData } from '../../components/data-table/models/tableColumn';
import { AddUsuarioComponent } from './add/add-usuario/add-usuario.component';
import { UsuarioService } from './service/usuario.service';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatSortModule, MatIconModule, MatCheckboxModule, MatSlideToggleModule,
    MatProgressSpinnerModule, MatPaginatorModule, MatFormFieldModule, MatInputModule, MatButtonModule, ReactiveFormsModule],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.scss'
})
export class UsuariosComponent {
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
  displayedColumns: string[] = ['nome', 'celular', 'email', 'perfil', 'ativo'];

  totalElements = 0;

  constructor(private usuarioService: UsuarioService) {
    this.dataSource = new MatTableDataSource<TableData>([]);
  }

  ngOnInit(): void {
    this.loadUsuarios();
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
    this.usuarioService.list(filter, this.mostrarInativos.value!, pageParams).subscribe(data => {
      this.dataSource.data = data.content;
      this.totalElements = data.totalElements;
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;

    this.loadUsuarios({
      page: this.pageIndex,
      linesPerPage: this.pageSize
    });
  }

  loadUsuarios(pageParams: Partial<PageParams> = {}): void {
    this.usuarioService.list('', this.mostrarInativos.value!, pageParams).subscribe(data => {
      this.dataSource.data = data.content;
      this.totalElements = data.totalElements;
    });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddUsuarioComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadUsuarios();
      }
    });
  }
}

// export class AddUsuarioComponentDialog {
//   readonly dialogRef = inject(MatDialogRef<AddUsuarioComponent>);
//   readonly data = inject<DialogData>(MAT_DIALOG_DATA);
//   readonly animal = model(this.data.animal);

//   onNoClick(): void {
//     this.dialogRef.close();
//   }
//}
