import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { debounceTime, finalize, switchMap, tap } from 'rxjs/operators';

import { Cidade } from '../../pages/cidade/cidade';
import { CidadeService } from '../../pages/cidade/services/cidade.service';
import { fromEvent } from 'rxjs';

@Component({
  selector: 'app-seletor-cidade',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './seletor-cidade.component.html',
  styleUrls: ['./seletor-cidade.component.scss'],
})
export class SeletorCidadeComponent implements OnInit {
  @Input() cidadeControl: FormControl<Cidade | null> = new FormControl(null);
  @Output() cidadeSelecionada = new EventEmitter<Cidade | null>();

  cidades: Cidade[] = [];
  isLoading = false;

  constructor(private cidadeService: CidadeService) {}

  ngOnInit(): void {}

  displayFn(cidade: Cidade | string | null): string {
    if (!cidade) {
      return '';
    }
    if (typeof cidade === 'string') {
      return cidade;
    }
    return `${cidade.nome} - ${cidade.uf}`;
  }

  onSelecionarCidade(cidade: Cidade | null): void {
    this.cidadeSelecionada.emit(cidade);
  }

  onInput(event: Event): void {
    const value = (event.target as HTMLInputElement).value;

    if (value) {
      this.isLoading = true;

      fromEvent(event.target as HTMLInputElement, 'input')
        .pipe(
          debounceTime(300),
          switchMap(() =>
            this.cidadeService
              .list(value, {
                page: 0,
                linesPerPage: 5,
                orderBy: 'nome',
                direction: 'ASC',
              })
              .pipe(finalize(() => (this.isLoading = false)))
          )
        )
        .subscribe((response) => {
          this.cidades = response?.content || [];
        });
    } else {
      this.cidades = [];
    }
  }
}
