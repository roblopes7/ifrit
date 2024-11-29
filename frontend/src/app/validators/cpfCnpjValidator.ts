import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function cpfCnpjValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value ? control.value.replace(/\D/g, '') : '';
    if (value.length === 11 || value.length === 14) {
      return null; // Válido
    }
    return { invalidCpfCnpj: true }; // Inválido
  };
}
