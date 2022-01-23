// @ts-ignore
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RealEstateNew} from '../model/real/real-estate-new';
import {Email} from '../model/real/email';
import {Direction} from '../model/real/direction';
import {RealEstateType} from '../model/real/real-estate-type';
import {ApprovalMail} from '../model/real/approval-mail';


// @ts-ignore
@Injectable({  providedIn: 'root' })
export class RealService {
  // private API = 'http://localhost:8080/real-estate-new';
  private API_URL = ' http://localhost:8080/api/real-estate-new';
  private API_URL_RELATED = 'http://localhost:8080/api/real-estate-new';
  private API_URL_HISTORY_POST = ' http://localhost:8080/api/real-estate-new/history-post';
  private API_URL_EMAIL = ' http://localhost:8080/api/real-estate-new/email';

  // khaipn
  private API_URL_LIST = 'http://localhost:8080/api/real-estate-new/search';

  constructor(private http: HttpClient) {
  }

// Tai
  findHistoryPostBySearchFieldId(page, customerId, title, kindOfNew, realNewType, approval): Observable<RealEstateNew[]> {
    return this.http.get<any>
    (this.API_URL_HISTORY_POST + '?page=' + page + '&customerId=' + customerId +
      '&title=' + title + '&kindOfNew=' + kindOfNew + '&realNewType=' + realNewType + '&approval=' + approval);
  }

  sendMail(email): Observable<Email> {
    return this.http.post<Email>(this.API_URL_EMAIL, email);
  }

  // khaiPN
  getAllRealEstates(): Observable<any> {
    return this.http.get(this.API_URL_LIST);
  }

  // KhaiPN

  getAllRealEstatesSearch(address: string, kindOfNews: any, realEstateType: any, direction: any,
                          minArea: any, maxArea: any, minPrice: string, maxPrice: string, page: number = 0): Observable<any> {
    const stringParam = this.buildSearchParam('?address=', address) +
      this.buildSearchParam('&kindOfNews=', kindOfNews) +
      this.buildSearchParam('&realEstateType=', realEstateType) +
      this.buildSearchParam('&direction=', direction) +
      this.buildSearchParam('&minArea=', minArea) +
      this.buildSearchParam('&maxArea=', maxArea) +
      this.buildSearchParam('&minPrice=', minPrice) +
      this.buildSearchParam('&maxPrice=', maxPrice) +
      this.buildSearchParam('&page=', page);

    return this.http.get(this.API_URL_LIST +
      stringParam
    );
  }


  // khaiPN
  private buildSearchParam(searchKey: string, value: string | number): string {
    if (!value) {
      return searchKey + '';
    }
    return searchKey + value.toString();
  }

  // TranNN
  save(realEstateNew: RealEstateNew): Observable<RealEstateNew> {
    return this.http.post<RealEstateNew>(this.API_URL + '/post', realEstateNew);
  }

  getAllListPostApproval(): Observable<any> {
    return this.http.get(this.API_URL + '/list-post-approval');
  }

  approve(id: string): Observable<any> {
    return this.http.delete(this.API_URL + '/approve' + id);
  }

// DOANH
  // 5.7.1 DoanhNV
  search(page: number, kindOfNews: string, direction: string, realEstateType: string): Observable<any> {
    return this.http.get<RealEstateNew[]>(this.API_URL + '/search-approval-list?kind_of_news=' + kindOfNews + '&direction_id=' + direction + '&real_estate_type_id=' + realEstateType + '&page=' + page);
  }

  // 5.7.1 DoanhNV
  acceptApprove(realEstateNew: RealEstateNew, id: string): Observable<any> {
    return this.http.post(this.API_URL + '/approve/' + id, realEstateNew);
  }

// 5.7.1 DoanhNV
  dontAcceptApprove(realEstateNew: RealEstateNew, id: string): Observable<any> {
    return this.http.post(this.API_URL + '/approval/' + id, realEstateNew);
  }

// 5.7.1 DoanhNV
  sendApprovalMail(email): Observable<ApprovalMail> {
    return this.http.post<ApprovalMail>(this.API_URL + '/approval-email', email);
  }


  getById(id): Observable<RealEstateNew[]> {
    return this.http.get<RealEstateNew[]>(this.API_URL + '/' + id).pipe();
  }


  getAllDirection(): Observable<Direction[]> {
    return this.http.get<Direction[]>(this.API_URL_RELATED + '/direction');
  }

  getAllRealEstateType(): Observable<RealEstateType[]> {
    return this.http.get<RealEstateType[]>(this.API_URL_RELATED + '/realEstateType');
  }

// // 5.7.1 DoanhNV
//   save(realEstateNew: RealEstateNew): Observable<RealEstateNew> {
//     return this.http.post<RealEstateNew>(this.API_URL + '/', realEstateNew);
//   }


// 5.7.1 DoanhNV
  findRealEstateNewById(id: string): Observable<RealEstateNew> {
    return this.http.get<RealEstateNew>(this.API_URL + '/detail/' + id);
  }

// DOANH
}

