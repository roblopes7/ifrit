import { Directive, HostListener, ElementRef } from '@angular/core';

@Directive({
  selector: '[appFormatCpfCnpj]'
})
export class FormatCpfCnpjDirective {

  constructor(private el: ElementRef) {}

  @HostListener('input', ['$event.target.value'])
  onInput(value: string): void {
    // Remove caracteres não numéricos
    const numericValue = value.replace(/\D/g, '');

    let formattedValue = value;

    // Aplica formatação baseada no comprimento do valor numérico
    if (numericValue.length === 11) {
      formattedValue = this.formatCpf(numericValue);
    } else if (numericValue.length === 14) {
      formattedValue = this.formatCnpj(numericValue);
    }

    // Atualiza o valor do input
    this.el.nativeElement.value = formattedValue;
  }

  private formatCpf(cpf: string): string {
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  }

  private formatCnpj(cnpj: string): string {
    return cnpj.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, '$1.$2.$3/$4-$5');
  }
}
