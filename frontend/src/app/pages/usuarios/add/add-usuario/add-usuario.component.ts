import { CommonModule } from '@angular/common';
import { Component, Inject, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { Perfil } from '../../models/perfil';
import { TelefoneMaskDirective } from '../../../../directives/telefone-mask.directive';
import { Usuario } from '../../models/usuario';
import { UsuarioService } from '../../service/usuario.service';

@Component({
  selector: 'app-add-usuario',
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
  ],
  templateUrl: './add-usuario.component.html',
  styleUrl: './add-usuario.component.scss',
})
export class AddUsuarioComponent {
  form: FormGroup;
  ativo = true;
  senha = signal(true);
  confirmarSenha = signal(true);

  perfis = Object.entries(Perfil).map(([key, value]) => ({ key, value }));
  perfilSelecionado: string | undefined;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddUsuarioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Usuario,
    private usuarioService: UsuarioService
  ) {
    this.form = this.fb.group(
      {
        nome: [data?.nome || '', Validators.required],
        login: [data?.login || '', Validators.required],
        senha: ['', [Validators.required, Validators.required]],
        confirmarSenha: ['', [Validators.required, Validators.required]],
        perfil: [
          data?.perfil || '',
          [Validators.required, Validators.required],
        ],
        telefone: [''],
        celular: [''],
        email: [''],
        ativo: [true],
      },
      {
        validators: this.senhasCorrespondemValidator,
      }
    );
  }

  mostraSenha(event: MouseEvent) {
    this.senha.set(!this.senha());
    event.stopPropagation();
  }

  mostraConfirmarSenha(event: MouseEvent) {
    this.confirmarSenha.set(!this.confirmarSenha());
    event.stopPropagation();
  }

  senhasCorrespondemValidator(
    formGroup: FormGroup
  ): { [key: string]: boolean } | null {
    const senha = formGroup.get('senha')?.value;
    const confirmarSenha = formGroup.get('confirmarSenha')?.value;
    if (senha && confirmarSenha && senha !== confirmarSenha) {
      return { senhasNaoCorrespondem: true };
    }
    return null;
  }

  onCancel() {
    this.dialogRef.close();
  }

  onSave() {
    if (this.form.valid) {
      const usuario: Usuario = {
        id: '',
        login: this.form.get('login')?.value,
        nome: this.form.get('nome')?.value,
        senha: this.form.get('senha')?.value,
        email: this.form.get('email')?.value,
        telefone: this.form.get('telefone')?.value,
        celular: this.form.get('celular')?.value,
        perfil: this.form.get('perfil')?.value,
        ativo: this.form.get('ativo')?.value,
      };

      this.usuarioService.upsert(usuario).subscribe(
        response => {
          console.log('Resposta do servidor:', response);
          this.dialogRef.close(response);
        },
        error => {
          console.error('Erro ao salvar o usuário:', error);
        }
      );
    }
  }
}
