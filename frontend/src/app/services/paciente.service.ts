import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estatisticas, Paciente, PacienteRequest } from '../models/paciente.model';

@Injectable({ providedIn: 'root' })
export class PacienteService {
  private readonly http = inject(HttpClient);
  private readonly url = '/api/pacientes';

  cadastrar(paciente: PacienteRequest): Observable<Paciente> {
    return this.http.post<Paciente>(this.url, paciente);
  }

  listar(): Observable<Paciente[]> {
    return this.http.get<Paciente[]>(this.url);
  }

  buscarPorNome(nome: string): Observable<Paciente[]> {
    const params = new HttpParams().set('nome', nome);
    return this.http.get<Paciente[]>(this.url, { params });
  }

  estatisticas(): Observable<Estatisticas> {
    return this.http.get<Estatisticas>(`${this.url}/estatisticas`);
  }
}