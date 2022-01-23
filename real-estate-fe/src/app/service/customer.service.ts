import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../model/customer/customer';
import {AppUser} from '../model/user/app-user';


@Injectable({
  providedIn: 'root'
})

export class CustomerService {
  // API - Thien
  private API = 'http://localhost:8080/api/customers';
  private API2 = 'http://localhost:8080/api/public/';
  private API_URL = 'http://localhost:8080/api/customers';

  constructor(
    public http: HttpClient,
  ) {
  }

  // thienlb
  getCustomerById(customerId): Observable<any> {
    return this.http.get(this.API + '/' + customerId);
  }

  // thienlb
  deleteCustomer(customerId): Observable<any> {
    return this.http.delete(this.API + '/delete-customer/' + customerId);
  }

  // thienlb
  findCustomer(page, customerName, customerPhone, customerEmail): Observable<any> {
    return this.http.get(this.API + '/customer-list?page=' + page + '&name=' + customerName
      + '&phone=' + customerPhone + '&email=' + customerEmail);
  }

  // thiện nhỏ
  findById(id: string): Observable<Customer> {
    return this.http.get<Customer>(this.API + `/id/` + id);
  }

  update(id: string, customer: Customer): Observable<Customer> {
    return this.http.patch<Customer>(this.API + `/edit-customer/` + id, customer);
  }

  changePassword(user: any): Observable<any> {
    return this.http.patch(this.API2 + `password`, user);
  }

  finduserbyusername(username: string): Observable<AppUser> {
    return this.http.get<AppUser>(this.API2 + `userName/` + username);
  }

  // Tung
  saveCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.API_URL + '/create', customer);
  }
  // Tung
  findCustomerById(id: string): Observable<any> {
    return this.http.get(this.API + '/detail/' + id);
  }
}
