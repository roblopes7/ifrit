import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { API_CONFIG } from '../../config/api.config';
import { DataTableResponseData } from '../../models/DataTableResponse';

@Injectable({
  providedIn: 'root',
})
export class CidadeService {

  private readonly API: string =  "/cidades"
  private readonly TOKEN: string = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpZnJpdCIsInN1YiI6IkFETUlOIiwiZXhwIjoxNzIxNjQ3NzQwfQ.aj8GpapcB4BFmBmXX8927yfzQi1CC3fXoq9BEsP4VuU"

  constructor(private http: HttpClient) { }

  list(){
    let params = new HttpParams()
    .set('direction', "ASC")
    .set('linesPerPage', 25)
    .set('orderBy', "nome")
    .set('page', 0);

    let headers = new HttpHeaders();
    headers = headers.append('Authorization', this.TOKEN);
    headers = headers.append('x-Flatten', 'true');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.get<DataTableResponseData>(`${API_CONFIG.baseUrl}${this.API}/lista`, { headers, params });
  }
}
