import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { QueryParamRequest } from '../../utils/request/QueryParamRequest';
import { Cidade } from '../cidade';

@Injectable({
  providedIn: 'root',
})
export class CidadeService {

  private readonly API = "cidades"

  constructor(private http: HttpClient) { }

  list(){
    let params = new HttpParams()
    .set('direction', "ASC")
    .set('linesPerPage', 25)
    .set('orderBy', "nome")
    .set('page', 0);

    let headers = new HttpHeaders();
    headers = headers.append('Authorization', "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpZnJpdCIsInN1YiI6IkFETUlOIiwiZXhwIjoxNzEwOTI4MTYyfQ.9Qmbs-ng674dDhKgO9KvFT9mmSaNvv12eaqWS0u5s4U");
    headers = headers.append('x-Flatten', 'true');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.get<Cidade[]>(`${this.API}/lista`, {headers, params})
  }
}
