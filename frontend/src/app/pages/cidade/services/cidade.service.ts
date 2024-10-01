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
  private readonly TOKEN: string = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpZnJpdCIsInN1YiI6IkFETUlOIiwiZXhwIjoxNzIyMzY1MjExfQ.U3p_ETp_0Qaj70oFM5fay6yMEy1w7pKh0Tom6Utaiiw"
  constructor(private http: HttpClient) { }

  list(pageParams: Partial<PageParams> = {}){
    const finalParams = getDefaultPageParams(pageParams);

    const params = new HttpParams()
      .set('direction', finalParams.direction)
      .set('linesPerPage', finalParams.linesPerPage.toString())
      .set('orderBy', finalParams.orderBy)
      .set('page', finalParams.page.toString());

    let headers = new HttpHeaders();
    headers = headers.append('Authorization', this.TOKEN);
    headers = headers.append('x-Flatten', 'true');
    headers = headers.append('Content-Type', 'application/json');

    return this.http.get<DataTableResponseData<Cidade>>(`${API_CONFIG.baseUrl}${this.API}/lista`, { headers, params });
  }
}
