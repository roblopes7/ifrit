import { CommonModule } from '@angular/common';
import { Component, Input, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';

import { DataTableResponseData } from '../../models/DataTableResponse';
import { TableColumn, TableData } from './models/tableColumn';

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [MatTableModule, CommonModule, MatPaginatorModule],
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.scss'
})
export class DataTableComponent {
  @Input() columns: TableColumn[] = [];
  @Input() data!: DataTableResponseData<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageIndex = 0;
  pageSize = 25;

  dataSource: MatTableDataSource<TableData>;
  displayedColumns: string[];

  constructor() {
    this.dataSource = new MatTableDataSource<TableData>([]);
    this.displayedColumns = [];
  }

  ngOnInit(): void {
    this.displayedColumns = this.columns.map(col => col.key);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['data']) {
      this.dataSource.data = this.data?.content || [];
    }
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
}
