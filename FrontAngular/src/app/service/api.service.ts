import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from "../model/user.model";
import {Observable} from "rxjs/index";
import {ApiResponse} from "../model/api.response";

@Injectable()
export class ApiService {

  constructor(private http: HttpClient) { }
  baseUrl: string = 'http://localhost:8080/SpringMockito/rest/users/';


 login(loginPayload) : Observable<ApiResponse> {
    return this.http.post<ApiResponse>('http://localhost:8080/SpringMockito/' + 'token/generate-token', loginPayload);

  }

  getUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getUserByLogin(login: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl+ "user/" + login);
  }

  createUser(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl , user);
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl+ "user/"  + user.login, user);
  }

  deleteUser(login: string): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + "user/" + login);
  }
}
