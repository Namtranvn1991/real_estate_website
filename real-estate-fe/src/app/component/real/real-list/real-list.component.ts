import {Component, OnInit} from '@angular/core';
import {RealService} from '../../../service/real.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';
import {RealEstateNew} from '../../../model/real/real-estate-new';
import {RealEstateType} from '../../../model/real/real-estate-type';
import {Direction} from '../../../model/real/direction';
import {isElementScrolledOutsideView} from "@angular/cdk/overlay/position/scroll-clip";

@Component({
  selector: 'app-real-list',
  templateUrl: './real-list.component.html',
  styleUrls: ['./real-list.component.scss']
})
export class RealListComponent implements OnInit {
  public realEstateNews: RealEstateNew[];
  mess: string;
  formSearch: FormGroup;
  public realEstateTypeList: RealEstateType[];
  directionList: Direction[];
  page = 0;
  totalPages = null;
  temp: string;
  realType: any;
  kindType: string;
  item: string;
  kindOfNewList = [
    {id: 1, name: 'Tin bán'},
    {id: 2, name: 'Tin cho thuê'}]
  ;
  priceRangeList = [
    {id: 1, minPrice: '0', maxPrice: '100000000', name: '0-100 triệu'},
    {id: 2, minPrice: '100000000', maxPrice: '500000000', name: '100-500 triệu'},
    {id: 3, minPrice: '500000000', maxPrice: '1000000000', name: '500triệu - 1 tỷ'},
    {id: 4, minPrice: '1000000000', maxPrice: '5000000000', name: '1-5 tỷ'},
    {id: 5, minPrice: '5000000000', maxPrice: '10000000000', name: '5 - 10 tỷ'},
    {id: 6, minPrice: '10000000000', maxPrice: '20000000000000', name: '10 - 20 tỷ'},
    {id: 7, minPrice: '20000000000', maxPrice: '10000000000000000000000', name: '20 tỷ +'}]
  ;

  areaRangeList = [
    {id: 1, minArea: '0', maxArea: '50', name: '0-50 m²'},
    {id: 2, minArea: '50', maxArea: '100', name: '50-100 m²'},
    {id: 3, minArea: '100', maxArea: '200', name: '100-200 m²'},
    {id: 4, minArea: '200', maxArea: '500', name: '200-500 m²'},
    {id: 5, minArea: '500', maxArea: '10000000000', name: '500+ m²'}
  ]
  ;

  constructor(
    private _formBuilder: FormBuilder,
    public realService: RealService,
    public activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit()
    :
    void {
    // lay real estate type list
    this.realService.getAllRealEstateType().subscribe(data => {
        this.realEstateTypeList = data;
      }
    );
    // lay real direction list
    this.realService.getAllDirection().subscribe(data => {
        this.directionList = data;
        console.log(this.directionList);
      }
    );
    // tao form search
    this.formSearch = new FormGroup({
      address: new FormControl(''),
      kindOfNews: new FormControl(''),
      realEstateType: new FormControl(''),
      direction: new FormControl(''),
      priceRange: new FormControl(''),
      areaRange: new FormControl(''),
      minArea: new FormControl(''),
      maxArea: new FormControl(''),
      minPrice: new FormControl(''),
      maxPrice: new FormControl(''),
    });
    // lay real estate list ban dau khi load trang
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
      this.temp = paramMap.get('temp');
      this.page = 0;
      this.totalPages = null;
      this.searchEstates(this.page).subscribe(data => {
        if (!data) {
          this.mess = 'Không có dữ liệu';
          return;
        }
        // Object destructoring es6
        const {totalPages, content} = data;
        // Optional chaining typescript
        if (content?.length) {
          this.mess = '';
          // spread operator es6
          this.realEstateNews = [...this.mappingData([...content])];
          this.totalPages = totalPages;
        } else {
          this.realEstateNews = [];
        }
        console.log(this.realEstateNews);
      });
    });

    // lay message gui tu page khac ve
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
      this.mess = paramMap.get('mess');
    });
  }

  private getTypeOfList(temp: string) {
    console.log(this.realEstateTypeList);
    // if (temp === '1') {
    //   this.formSearch.value.realEstateType = this.realEstateTypeList[0];
    // } else {
    //   this.formSearch.value.realEstateType.id = this.realEstateTypeList[1];
    // }
  }

// method khi an button search
  onSearch() {
    this.temp = '0';
    this.page = 0;
    this.totalPages = null;
    this.searchEstates(this.page)
      .subscribe(data => {
        if (!data) {
          this.mess = 'Nội dung bạn tìm không có';
          this.page = -1;
          this.totalPages = null;
          this.realEstateNews = [];
          return;
        }
        const {totalPages, content} = data;
        if (content?.length) {
          this.mess = '';
          this.realEstateNews = [...this.mappingData([...content])];
          this.totalPages = totalPages;
        } else {
          this.realEstateNews = [];
        }
      });
    console.log(this.realEstateNews);
  }

// method khi an "xem them+"
  onLoadMore() {
    this.page += 1;
    if (this.page >= this.totalPages) {
      return;
    }
    this.searchEstates(this.page)
      .subscribe(data => {
        if (!data) {
          this.totalPages = 0;
          return;
        }
        const {totalPages, content} = data;

        if (content?.length) {
          this.realEstateNews = [...this.realEstateNews, ...this.mappingData([...content])];
          this.totalPages = totalPages;

        } else {
          this.realEstateNews = [];
        }
      });
  }

// method khi nhan button scroll to top
  onScrollToTop(e: any) {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }

// method tuong tac voi service
  private searchEstates(page: number): Observable<any> {
    switch (this.temp) {
      case '1':
        this.realType = '1';
        break;
      case '2':
        this.realType = '2';
        break;
      case '3':
        this.kindType = '1';
        break;
      case '4':
        this.kindType = '2';
        break;
      default:
        this.realType = this.formSearch.value.realEstateType.id;
        this.kindType = this.formSearch.value.kindOfNews.id;
    }
    console.log('loại bds: ' + this.realType);
    console.log('loại tin: ' + this.kindType);
    return this.realService.getAllRealEstatesSearch(
      this.formSearch.value.address,
      this.kindType,
      this.realType,
      this.formSearch.value.direction.id,
      this.formSearch.value.areaRange.minArea,
      this.formSearch.value.areaRange.maxArea,
      this.formSearch.value.priceRange.minPrice,
      this.formSearch.value.priceRange.maxPrice,
      page
    );
  }

  // mapping data raw nhan duoc tu api
  private mappingData(content: any[]): any {
    if (!content) {
      return [];
    }
    return content.map(item => {
      if (item.price / 1000000000 >= 1) {
        item.displayedPrice = Math.round(item.price) / 1000000000 + ' Tỷ';
      } else {
        item.displayedPrice = Math.round(item.price) / 1000000 + ' Triệu';
      }
      item = {...item, ...item.displayedPrice};
      return item;
    });
  }
}
