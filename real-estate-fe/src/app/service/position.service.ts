import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Positions} from '../model/employee/positions';

@Injectable({
  providedIn: 'root'
})
export class PositionService {
  private API_URL = 'http://localhost:8080/api/employee/position';

  constructor( private httpClient: HttpClient) {
  }
  getAllPosition(): Observable<Positions[]> {
    return this.httpClient.get<Positions[]>(this.API_URL);
  }
}
