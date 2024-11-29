import { Injectable } from '@angular/core';
import { PageParams } from '../../../components/data-table/models/pageParams';
import { getDefaultPageParams } from '../../../components/data-table/models/defaultPageParams';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { DataTableResponseData } from '../../../models/DataTableResponse';
import { Empresa } from '../models/empresa';
import { API_CONFIG } from '../../../config/api.config';

@Injectable({
  providedIn: 'root',
})
export class EmpresaService {
  private readonly API: string = '/empresas';
  constructor(private http: HttpClient) {}

  list(
    filter: string,
    pageParams: Partial<PageParams> = {}
  ) {
    const finalParams = getDefaultPageParams(pageParams);

    const params = new HttpParams()
      .set('direction', finalParams.direction)
      .set('linesPerPage', finalParams.linesPerPage.toString())
      .set('orderBy', finalParams.orderBy)
      .set('page', finalParams.page.toString())
      .set('filter', filter);

    let headers = new HttpHeaders();
    headers = headers.append('x-Flatten', 'true');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.get<DataTableResponseData<Empresa>>(
      `${API_CONFIG.baseUrl}${this.API}/filtro`,
      { headers, params }
    );
  }

  upsert(empresa: Empresa) {
    const url = `${API_CONFIG.baseUrl}${this.API}${
      empresa.id ? `/${empresa.id}` : ''
    }`;

    if (empresa.id) {
      return this.http.put<Empresa>(url, empresa);
    } else {
      return this.http.post<Empresa>(url, empresa);
    }
  }
}
