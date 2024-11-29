import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'telefone',
  standalone: true,
})
export class TelefonePipe implements PipeTransform {
  transform(value: string | null): string {
    if (!value) return '';

    let cleanedValue = value.replace(/\D/g, '');

    if (cleanedValue.length === 8) {
      return `${cleanedValue.substring(0, 4)}-${cleanedValue.substring(4)}`;
    }

    if (cleanedValue.length === 9) {
      return `${cleanedValue.substring(0, 5)}-${cleanedValue.substring(5)}`;
    }

    if (cleanedValue.length > 6) {
      return `(${cleanedValue.substring(0, 2)}) ${cleanedValue.length === 11
        ? cleanedValue.substring(2, 7) + '-' + cleanedValue.substring(7)
        : cleanedValue.substring(2, 6) + '-' + cleanedValue.substring(6)}`;
    }

    return cleanedValue;
  }
}
