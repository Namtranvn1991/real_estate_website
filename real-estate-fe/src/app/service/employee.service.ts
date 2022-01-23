import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Employee} from '../model/employee/employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  public API = 'http://localhost:8080/api/employee';

  constructor(private http: HttpClient) {
  }

  getEmployee(id: string): Observable<any> {
    return this.http.get(this.API + '/detail/' + id);
  }

  search(page: number, name: string, email: string, positon: string): Observable<any> {
    return this.http.get(this.API + '/search?name=' + name + '&email=' + email +
      '&position_id=' + positon + '&page=' + page);
  }

  deleteEmployeeById(id: string): Observable<any> {
    return this.http.delete(this.API + '/delete/' + id);
  }

  save(employee: any): Observable<any> {
    return this.http.post(this.API + '/create/', employee);
  }

  findEmployeeById(id: string): Observable<any> {
    return this.http.get(this.API + '/edit/' + id);
  }

  updateEmployee(id: string, employee: Employee): Observable<any> {
    return this.http.patch(this.API + '/edit/' + id, employee);
  }
}
