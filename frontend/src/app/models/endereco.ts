import { Cidade } from "../pages/cidade/cidade";

export interface Endereco {
  logradouro: string;
  numero: string;
  bairro: string;
  cep: string;
  cidade: Cidade | null;
}
