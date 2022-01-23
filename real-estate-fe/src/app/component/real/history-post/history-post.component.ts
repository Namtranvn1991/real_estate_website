import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {FormBuilder, FormGroup} from '@angular/forms';
import {RealService} from '../../../service/real.service';
import {Subscription} from 'rxjs';
import {RealEstateNew} from '../../../model/real/real-estate-new';

@Component({
  selector: 'app-history-post',
  templateUrl: './history-post.component.html',
  styleUrls: ['./history-post.component.scss']
})
export class HistoryPostComponent implements OnInit {
  private subscription: Subscription;
  realEstateNews: RealEstateNew[];
  realNews: RealEstateNew[];
  customerId: string;
  totalPages: number;
  pageNumber: number;
  size = 0;
  page = 0;
  title = '';
  kindOfNew = '';
  realNewType = '';
  approval = '';
  message: string;
  clickPage1 = false;
  clickPage2 = false;
  buttonDisabled = false;
  flagSearch = false;
  numberNoAccept = 0;
  numberwaiterAccept = 0;

  constructor(
    public  realService: RealService,
    public activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
      this.customerId = paramMap.get('id');
      console.log(this.customerId);
      this.subscription = this.realService.findHistoryPostBySearchFieldId(
        this.page, this.customerId, this.title, this.kindOfNew, this.realNewType, '3').subscribe(realData => {
        this.numberNoAccept = realData['content'].length
      });
      this.subscription = this.realService.findHistoryPostBySearchFieldId(
        this.page, this.customerId, this.title, this.kindOfNew, this.realNewType, '1').subscribe(realWaitData => {
        this.numberwaiterAccept = realWaitData['content'].length
      });
    });
    this.showRealEstate();
  }

  showAllList() {
    console.log(this.page);
    this.subscription = this.realService.findHistoryPostBySearchFieldId(
      this.page, this.customerId, '', '', '', ''
    ).subscribe(data => {
      console.log(data);
      if (data != null) {
        this.realEstateNews = data['content'];
        this.totalPages = data['totalPages'];
        this.size = data['size'];
        this.pageNumber = data['pageable'].pageNumber;
        console.log(this.pageNumber);
        console.log(this.page);
      } else {
        this.realEstateNews = null;
      }
    });
  }

  showRealEstate() {
    if (this.title === '' && this.kindOfNew === '' && this.realNewType === '') {
      this.flagSearch = false;
      this.getRealEstate();
    } else if (this.title !== '' && this.kindOfNew === '' && this.realNewType === '') {
      this.searchRealEstate();
    } else if (this.title !== '' && this.kindOfNew !== '' && this.realNewType === '') {
      this.searchRealEstate();
    } else if (this.title !== '' && this.kindOfNew !== '' && this.realNewType !== '') {
      this.searchRealEstate();
    } else if (this.title === '' && this.kindOfNew !== '' && this.realNewType === '') {
      this.searchRealEstate();
    } else if (this.title === '' && this.kindOfNew !== '' && this.realNewType !== '') {
      this.searchRealEstate();
    } else if (this.title === '' && this.kindOfNew === '' && this.realNewType !== '') {
      this.searchRealEstate();
    } else if (this.title !== '' && this.kindOfNew === '' && this.realNewType !== '') {
      this.searchRealEstate();
    }
  }

  getRealEstate() {
    this.realService.findHistoryPostBySearchFieldId(this.page,
      this.customerId, this.title, this.kindOfNew, this.realNewType, this.approval).subscribe(data => {
      console.log(data);
      if (data !== null) {
        this.realEstateNews = data['content'];
        this.totalPages = data['totalPages'];
        this.size = data['size'];
        this.pageNumber = data['pageable'].pageNumber;
        console.log(this.pageNumber);
        console.log(this.kindOfNew);
        console.log(this.realNewType);
        this.message = '';
        console.log(this.message);
      } else {
        this.message = 'Not found !!!';
        console.log(this.message);
      }
    });
  }

  searchRealEstate() {
    if (this.flagSearch === false) {
      this.page = 0;
      this.getRealEstate();
      this.flagSearch = true;
    } else {
      this.getRealEstate();
      this.flagSearch = true;
    }
  }

  onSubmit() {
    this.flagSearch = false;
    this.getRealEstate();
  }

  showAll() {
    this.page = 0;
    this.title = '';
    this.kindOfNew = '';
    this.realNewType = '';
    this.approval = '';
    this.ngOnInit();
  }

  accept() {
    console.log('accept')
    this.page = 0;
    this.subscription = this.realService.findHistoryPostBySearchFieldId(
      this.page, this.customerId, this.title, this.kindOfNew, this.realNewType, 2).subscribe(data => {
      console.log(data);
      if (data != null) {
        this.realEstateNews = data['content'];
        this.totalPages = data['totalPages'];
        this.size = data['size'];
        this.pageNumber = data['pageable'].pageNumber;
      } else {
        this.realEstateNews = null;
      }
    });
  }

  noAccept() {
    console.log('no accept')
    this.page = 0;
    this.subscription = this.realService.findHistoryPostBySearchFieldId(
      this.page, this.customerId, this.title, this.kindOfNew, this.realNewType, 3).subscribe(data => {
      console.log(data);
      if (data != null) {
        this.realEstateNews = data['content'];
        this.totalPages = data['totalPages'];
        this.size = data['size'];
        this.pageNumber = data['pageable'].pageNumber;
      } else {
        this.realEstateNews = null;
      }
    });
  }

  waitAccept() {
    console.log('wait accept')
    this.page = 0;
    this.subscription = this.realService.findHistoryPostBySearchFieldId(
      this.page, this.customerId, this.title, this.kindOfNew, this.realNewType, 1).subscribe(data => {
      console.log(data);
      if (data != null) {
        this.realEstateNews = data['content'];
        this.totalPages = data['totalPages'];
        this.size = data['size'];
        this.pageNumber = data['pageable'].pageNumber;
      } else {
        this.realEstateNews = null;
      }
    });
  }

  previousClick(index) {
    this.page = this.page - index;
    console.log('pre pay ' + this.page + '/' + this.totalPages + 'search:' + this.flagSearch);
    this.ngOnInit();
  }

  nextClick(index) {
    this.page = this.page + index;
    console.log('next pay ' + this.page + '/' + this.totalPages + 'search:' + this.flagSearch);
    this.ngOnInit();
  }


  findPaginnation(value: number) {
    this.page = value - 1;
    this.ngOnInit();
  }

  sell() {
    this.page = 0;
    this.subscription = this.realService.findHistoryPostBySearchFieldId(
      this.page, this.customerId, this.title, 1, this.realNewType, this.approval).subscribe(data => {
      console.log(data);
      if (data != null) {
        this.realEstateNews = data['content'];
        this.totalPages = data['totalPages'];
        this.size = data['size'];
        this.pageNumber = data['pageable'].pageNumber;
      } else {
        this.realEstateNews = null;
      }
    });

  }

  forRent() {
    this.page = 0;
    this.subscription = this.realService.findHistoryPostBySearchFieldId(
      this.page, this.customerId, this.title, 2, this.realNewType, this.approval).subscribe(data => {
      console.log(data);
      if (data != null) {
        this.realEstateNews = data['content'];
        this.totalPages = data['totalPages'];
        this.size = data['size'];
        this.pageNumber = data['pageable'].pageNumber;
      } else {
        this.realEstateNews = null;
      }
    });
  }
}
