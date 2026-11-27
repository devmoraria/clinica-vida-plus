import { Component } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

registerLocaleData(localePt);

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly ano = new Date().getFullYear();
}