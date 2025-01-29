import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import {Formula} from './formula.module';

@Injectable({
  providedIn: 'root',
})
export class EssenseService {
  private baseUrl = 'http://localhost:8083/api/app/formulas';

  constructor(private httpClient: HttpClient) {}


  getFormulasByType(type: string): Observable<any[]> {
    const url = `${this.baseUrl}/${type}`;
    return this.httpClient.get<any[]>(url).pipe(
      tap((response) => {
        console.log(`Получены формулы для типа: ${type}`, response);
      }),
      catchError((error) => {
        console.error('Ошибка при получении формул:', error);
        return throwError(() => error);
      })
    );
  }

  deleteFormula(formula: Formula, category: string): Observable<void> {
    const url = `${this.baseUrl}/${category}/${formula.id}`;
    return this.httpClient.delete<void>(url).pipe(
      tap(() => {
        console.log(`Формула с id ${formula.id} успешно удалена`);
      }),
      catchError((error) => {
        console.error('Ошибка при удалении формулы:', error);
        return throwError(() => error);
      })
    );
  }

  getStudentsByTeacherCode(teacherCode: string): Observable<any> {
    return this.httpClient.get<any>(`http://localhost:8083/api/app/teacher/students?code=${teacherCode}`);
  }
  getAllUsers() {
    return this.httpClient.get(`http://localhost:8083/api/app/admin/users`);
  }

  deleteUser(userId: number) {
    return this.httpClient.delete(`http://localhost:8083/api/app/admin/users/${userId}`);
  }

}
