import { Component, inject } from '@angular/core';
import { JsonPipe } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { PacienteService } from '../../services/paciente.service';

@Component({
  selector: 'app-listar',
  imports: [JsonPipe],
  template: `<pre>{{ pacientes() | json }}</pre>`,
})
export class Listar {
  protected readonly pacientes = toSignal(inject(PacienteService).listar());
}