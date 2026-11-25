export interface Paciente {
  id: number;
  nome: string;
  idade: number;
  telefone: string;
}

export interface PacienteRequest {
  nome: string;
  idade: number;
  telefone: string;
}

export interface Estatisticas {
  total: number;
  idadeMedia: number;
  maisNovo: Paciente | null;
  maisVelho: Paciente | null;
}