import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root' // Esto es lo que le dice a Angular que puede inyectar este servicio en cualquier parte
  })
  export class AuthService {
    private apiUrl = 'http://localhost:8080/auth'; // Cambia si tu backend usa otro puerto
  
    constructor(private http: HttpClient) {}
  
    login(username: string, password: string): Observable<any> {
      return this.http.post(`${this.apiUrl}/login`, {
        userName: username,
        password: password
      }, { responseType: 'text' });
    }
  }
