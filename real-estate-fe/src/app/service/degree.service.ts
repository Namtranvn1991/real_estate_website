import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Degree} from '../model/employee/degree';

@Injectable({
  providedIn: 'root'
})
export class DegreeService {
  private API_URL = 'http://localhost:8080/api/employee/degree';

  constructor(private httpClient: HttpClient) {
  }

  getAllDegree(): Observable<Degree[]> {
    return this.httpClient.get<Degree[]>(this.API_URL);
  }
}
