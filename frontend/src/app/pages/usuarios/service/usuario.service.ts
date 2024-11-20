import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { getDefaultPageParams } from '../../../components/data-table/models/defaultPageParams';
import { PageParams } from '../../../components/data-table/models/pageParams';
import { API_CONFIG } from '../../../config/api.config';
import { DataTableResponseData } from '../../../models/DataTableResponse';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private readonly API: string =  "/usuarios"
  constructor(private http: HttpClient) { }

  list(filter:  string, mostrarInativos: boolean, pageParams: Partial<PageParams> = {}){
    const finalParams = getDefaultPageParams(pageParams);

    const params = new HttpParams()
      .set('direction', finalParams.direction)
      .set('linesPerPage', finalParams.linesPerPage.toString())
      .set('orderBy', finalParams.orderBy)
      .set('page', finalParams.page.toString())
      .set('inativos', mostrarInativos)
      .set('filter', filter);

    let headers = new HttpHeaders();
    headers = headers.append('x-Flatten', 'true');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.get<DataTableResponseData<Usuario>>(`${API_CONFIG.baseUrl}${this.API}/filtro`, { headers, params });
  }

  upsert(usuario: Usuario) {
    const url = `${API_CONFIG.baseUrl}${this.API}${usuario.id ? `/${usuario.id}` : ''}`;
    console.log('URL:', url);
    console.log('Usuário:', usuario);

    if (usuario.id) {
      return this.http.put<Usuario>(url, usuario);
    } else {
      return this.http.post<Usuario>(url, usuario);
    }
  }

}
