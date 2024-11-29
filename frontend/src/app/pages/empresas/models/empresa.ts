import { Contato } from "../../../models/contato";
import { Endereco } from "../../../models/endereco";

export interface Empresa {
  id: string;
  razaoSocial: string;
  nomeFantasia: string;
  cnpjCpf: string;
  responsavel: string;
  contato: Contato;
  endereco: Endereco;
}
