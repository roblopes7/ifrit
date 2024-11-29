import { SeletorCidadeComponent } from '../../../components/seletor-cidade/seletor-cidade.component';
import { TelefoneMaskDirective } from '../../../directives/telefone-mask.directive';
import { cpfCnpjValidator } from '../../../validators/cpfCnpjValidator';
import { Cidade } from '../../cidade/cidade';
import { Empresa } from '../models/empresa';
import { EmpresaService } from '../services/empresa.service';
import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit, signal } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTabsModule } from '@angular/material/tabs';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-add-empresa',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatSlideToggleModule,
    TelefoneMaskDirective,
    MatTabsModule,
    MatDialogModule,
    SeletorCidadeComponent,
  ],
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss', '../../../styles/add-styles.scss'],
})
export class AddEmpresaComponent {
  empresaForm!: FormGroup;
  ativo = true;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddEmpresaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Empresa,
    private empresaService: EmpresaService
  ) {
    this.empresaForm = this.fb.group({
      razaoSocial: [data?.razaoSocial || '', Validators.required],
      nomeFantasia: [data?.nomeFantasia || '', Validators.required],
      cnpjCpf: ['', [Validators.required, cpfCnpjValidator()]],
      responsavel: [data?.responsavel || ''],
      endereco: this.fb.group({
        logradouro: [data?.endereco?.logradouro || '', Validators.required],
        numero: [data?.endereco?.numero || '', Validators.required],
        bairro: [data?.endereco?.bairro || '', Validators.required],
        cep: [data?.endereco?.cep || ''],
        cidade: new FormControl<Cidade | null>(null, Validators.required),
      }),
      contato: this.fb.group({
        email: [data?.contato?.email || '', [Validators.email]],
        telefone: [data?.contato?.telefone || ''],
        celular: [data?.contato?.celular || ''],
        responsavel: [data?.contato?.responsavel || ''],
      }),
    });
  }

  ngOnInit() {
    // this.empresaForm.valueChanges.subscribe((valores) => {
    //   console.log('Valores do FormGroup alterados:', valores);
    // });
  }

  onCancel() {
    this.dialogRef.close();
  }

  onSave() {
    if (this.empresaForm.valid) {
      const empresa: Empresa = this.getEmpresa();

      this.empresaService.upsert(empresa).subscribe(
        (response) => {
          console.log('Resposta do servidor:', response);
          this.dialogRef.close(response);
        },
        (error) => {
          console.error('Erro ao salvar o empresa:', error);
        }
      );
    }
  }

  getEmpresa() {
    return {
      id: this.data?.id || '',
      razaoSocial: this.empresaForm.value.razaoSocial,
      nomeFantasia: this.empresaForm.value.nomeFantasia,
      cnpjCpf: this.empresaForm.value.cnpjCpf,
      responsavel: this.empresaForm.value.responsavel,
      endereco: {
        logradouro: this.empresaForm.value.endereco.logradouro,
        numero: this.empresaForm.value.endereco.numero,
        bairro: this.empresaForm.value.endereco.bairro,
        cep: this.empresaForm.value.endereco.cep,
        cidade: {
          id: this.empresaForm.value.endereco.cidade?.id || null
        },
      },
      contato: {
        email: this.empresaForm.value.contato.email,
        email2: this.empresaForm.value.contato.email2 || null,
        telefone: this.empresaForm.value.contato.telefone || null,
        telefone2: this.empresaForm.value.contato.telefone2 || null,
        celular: this.empresaForm.value.contato.celular || null,
        celular2: this.empresaForm.value.contato.celular2 || null,
        responsavel: this.empresaForm.value.contato.responsavel || null,
      },
    };
  }

  aoSelecionarCidade(cidade: Cidade | null): void {
    this.empresaForm.get('endereco.cidade')?.setValue(cidade);
  }

  get cidadeControl(): FormControl<Cidade | null> {
    return this.empresaForm.get('endereco.cidade') as FormControl<Cidade | null>;
  }
}
