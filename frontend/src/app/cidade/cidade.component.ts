import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';

import { CidadeService } from './service/cidade.service';

@Component({
  selector: 'app-cidade',
  standalone: true,
  imports: [MatToolbarModule],
  templateUrl: './cidade.component.html',
  styleUrl: './cidade.component.scss'
})
export class CidadeComponent {

  constructor(private cidadeService: CidadeService) {}
}
