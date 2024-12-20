import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { getDefaultPageParams } from '../../../components/data-table/models/defaultPageParams';
import { PageParams } from '../../../components/data-table/models/pageParams';
import { API_CONFIG } from '../../../config/api.config';
import { DataTableResponseData } from '../../../models/DataTableResponse';
import { Cidade } from '../cidade';

@Injectable({
  providedIn: 'root',
})
export class CidadeService {

  private readonly API: string =  "/cidades"
  constructor(private http: HttpClient) { }

  list(filter:  string, pageParams: Partial<PageParams> = {}){
    const finalParams = getDefaultPageParams(pageParams);

    const params = new HttpParams()
      .set('direction', finalParams.direction)
      .set('linesPerPage', finalParams.linesPerPage.toString())
      .set('orderBy', finalParams.orderBy)
      .set('page', finalParams.page.toString())
      .set('filter', filter);

    let headers = new HttpHeaders();
    //headers = headers.append('Authorization', this.TOKEN);
    headers = headers.append('x-Flatten', 'true');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.get<DataTableResponseData<Cidade>>(`${API_CONFIG.baseUrl}${this.API}/filtro`, { headers, params });
  }

  updateIbge(){
    const params = new HttpParams();
    let headers = new HttpHeaders();
    //headers = headers.append('Authorization', this.TOKEN);
    headers = headers.append('x-Flatten', 'true');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.post(`${API_CONFIG.baseUrl}${this.API}/consultar-ibge`, { headers, params });
  }
}
