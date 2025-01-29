import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private baseUrl = 'http://localhost:8083/api/app/auth';
  private userData: any = null;
  constructor(private httpClient: HttpClient) {}

  registerUser(data: any): Observable<any> {
    const url = `${this.baseUrl}/register`;
    console.log('Регистрация пользователя, отправка запроса на URL: ', url, data);

    return this.httpClient.post(url, data).pipe(
      tap(response => {
        console.log('Ответ от сервера:', response);
      }),
      catchError(error => {
        console.error('Ошибка при регистрации пользователя', error);
        return of(null);
      })
    );
  }



  loginUser(data: any): Observable<any> {
    const url = `${this.baseUrl}/login`;
    console.log(`Вход пользователя, отправка запроса на URL: ${url}`, data);

    return this.httpClient.post(url, data).pipe(
      tap((response: any) => {
        if (response && response.token) {
          const savedToken = localStorage.getItem('sessionId');

          localStorage.setItem('sessionId', response.token);
          sessionStorage.setItem('sessionId', response.token);
          // Сравниваем текущий и новый токен
          if (savedToken !== response.token) {
            console.log('Токен обновлен');
            localStorage.setItem('sessionId', response.token);  // Обновляем токен в localStorage
            sessionStorage.setItem('sessionId', response.token);  // Обновляем токен в sessionStorage

          }
        }
      }),
      catchError(error => {
        console.error('Ошибка при входе', error);
        return of(null);
      })
    );
  }

  logoutUser(data:any): Observable<any> {
    const url = `${this.baseUrl}/logout`;

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
    });
    localStorage.removeItem('username');

    return this.httpClient.delete(url, { headers });
  }


  getUserData() {
    console.log(sessionStorage)
    console.log(localStorage)
    const userData = {
      username: sessionStorage.getItem('username'),
      email: sessionStorage.getItem('email'),
      firstName: sessionStorage.getItem('firstName'),
      role: sessionStorage.getItem('role'),
      teacherCode: sessionStorage.getItem('teacherCode')
    };
    return userData;
  }


  updateProgress(username: string, progress: number): Observable<any> {
    const url = 'http://localhost:8083/api/app/exerciseOne/progress'; // Убедитесь, что URL соответствует вашему API
    const body = {
      username: username,
      progressResult: progress
    };

    return this.httpClient.post(url, body).pipe(
      tap(response => {
        console.log('Прогресс успешно обновлён на сервере:', response);
      }),
      catchError(error => {
        console.error('Ошибка при обновлении прогресса:', error);
        return throwError(() => error); // Используем throwError для передачи ошибки
      })
    );
  }
  getProgress(username: string): Observable<any> {
    const url = `http://localhost:8083/api/app/exerciseOne/progress/${username}`; // URL для получения прогресса

    return this.httpClient.get(url).pipe(
      tap(response => {
        console.log('Прогресс пользователя получен:', response);
      }),
      catchError(error => {
        console.error('Ошибка при получении прогресса:', error);
        return throwError(() => error);
      })
    );
  }

  getUserRole(): string | null {
    return sessionStorage.getItem('role');
  }


  getSubscriptionStatus(username: string) {
    return this.httpClient.get<{ subscriptionStatus: string }>(`http://localhost:8083/api/app/subscription/status/${username}`).pipe(
      catchError(error => {
        console.error('Ошибка при получении статуса подписки:', error);
        return throwError(() => error);
      })
    );
  }


  buySubscription(username: string) {
    this.httpClient.post(`http://localhost:8083/api/app/subscription/buy/${username}`, {}).subscribe(
      response => {
        console.log('Подписка успешно активирована', response);
        this.getSubscriptionStatus(username);
      },
      error => {
        console.error('Ошибка при активации подписки', error);
      }
    );
  }

}




