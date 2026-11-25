import { Routes } from '@angular/router';
import { Cadastrar } from './pages/cadastrar/cadastrar';
import { Listar } from './pages/listar/listar';
import { Buscar } from './pages/buscar/buscar';
import { Estatisticas } from './pages/estatisticas/estatisticas';

export const routes: Routes = [
  { path: '', redirectTo: 'listar', pathMatch: 'full' },
  { path: 'cadastrar', component: Cadastrar },
  { path: 'listar', component: Listar },
  { path: 'buscar', component: Buscar },
  { path: 'estatisticas', component: Estatisticas },
  { path: '**', redirectTo: 'listar' },
];