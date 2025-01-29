// formula.service.ts
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FormulaService {

  private apiUrl = 'http://localhost:8073/api/app/formulas';

  constructor(private httpClient: HttpClient) {}

  getFormulas(): Observable<any> {
    const url = `${this.apiUrl}/all`;
    const token = localStorage.getItem("sessionId");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get(url, {headers})
  }
}
