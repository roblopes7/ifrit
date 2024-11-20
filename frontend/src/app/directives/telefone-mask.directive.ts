import { Directive, HostListener, ElementRef } from '@angular/core';

@Directive({
  selector: '[telefoneMask]',
  standalone: true
})
export class TelefoneMaskDirective {
  constructor(private el: ElementRef) {}

  @HostListener('input', ['$event'])
  onInputChange(event: Event): void {
    const input = this.el.nativeElement as HTMLInputElement;
    let value = input.value.replace(/\D/g, ''); // Remove todos os caracteres não numéricos

    if (value.length > 11) {
      value = value.substring(0, 11); // Limita o valor a 11 dígitos
    }

    // Aplica a máscara (XX) XXXXX-XXXX
    if (value.length > 6) {
      value = `(${value.substring(0, 2)}) ${value.substring(2, 7)}-${value.substring(7)}`;
    } else if (value.length > 2) {
      value = `(${value.substring(0, 2)}) ${value.substring(2)}`;
    }

    input.value = value; // Atualiza o valor no campo de entrada
  }
}
