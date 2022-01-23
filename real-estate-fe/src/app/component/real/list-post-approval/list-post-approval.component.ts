import {Component, OnInit} from '@angular/core';
import {RealEstateNew} from '../../../model/real/real-estate-new';
import {RealService} from '../../../service/real.service';
import {MatDialog} from '@angular/material/dialog';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Subscription} from 'rxjs';
import {ApprovalMail} from '../../../model/real/approval-mail';
import {Router} from '@angular/router';
import {DeletePostApprovalComponent} from '../delete-post-approval/delete-post-approval.component';
import {DetailPostApprovalComponent} from '../detail-post-approval/detail-post-approval.component';


@Component({
  selector: 'app-list-post-approval',
  templateUrl: './list-post-approval.component.html',
  styleUrls: ['./list-post-approval.component.scss']
})
export class ListPostApprovalComponent implements OnInit {
  realEstateNews: RealEstateNew[];
  realEstateNew: RealEstateNew;
  realForm: FormGroup;
  emailForm: FormGroup;

  private subscription: Subscription | undefined;

  page = 0;
  kindOfNews = '';
  direction = '';
  realEstateType = '';
  totalPages: number;
  pageNumber: number;
  size = 0;
  flag = false;
  message: string;
  id: string;
  approvalMail: ApprovalMail;
  customerEmail: string;


  kindOfNewsList = [{id: 1, name: 'Bán'}, {id: 2, name: 'Cho thuê'}];

  directionList = [{id: 1, name: 'Đông'}, {id: 2, name: 'Đông Nam'}, {id: 3, name: 'Đông Bắc'},
    {id: 4, name: 'Tây'}, {id: 5, name: 'Tây Nam'}, {id: 6, name: 'Tây Bắc'}, {id: 7, name: 'Nam'}, {
      id: 8, name: 'Bắc'
    }];

  realEstateTypeList = [{id: 1, name: 'Đất'}, {id: 2, name: 'Nhà ở'}];

  constructor(private realService: RealService, public dialog: MatDialog, private router: Router, private form: FormBuilder) {
  }

  ngOnInit(): void {
    this.search();
  }

  search() {
    if (this.kindOfNews === '' && this.direction === '' && this.realEstateType === '') {
      this.flag = false;
      this.realService.search(this.page, this.kindOfNews, this.direction, this.realEstateType).subscribe(data => {
        console.log(data);
        if (data !== null) {
          this.realEstateNews = data.content;
          this.totalPages = data.totalPages;
          this.pageNumber = data.pageable.pageNumber;
          this.size = data.size;
          this.page = data.pageable.pageNumber;
          this.message = '';
        } else {
          this.message = 'Không tìm thấy';
          this.realEstateNews = [];
          this.totalPages = 0;
        }
      });
    } else {
      if (this.flag === false) {
        this.page = 0;
        this.realService.search(this.page, this.kindOfNews, this.direction, this.realEstateType).subscribe(data => {
          if (data !== null) {
            this.realEstateNews = data.content;
            this.totalPages = data.totalPages;
            this.pageNumber = data.pageable.pageNumber;
            this.size = data.size;
            this.page = data.pageable.pageNumber;
            this.message = '';
          } else {
            this.message = 'Không tìm thấy';
            this.realEstateNews = [];
            this.totalPages = 0;
          }
          this.flag = true;
        });
      } else {
        this.realService.search(this.page, this.kindOfNews, this.direction, this.realEstateType).subscribe(data => {
          if (data !== null) {
            this.realEstateNews = data.content;
            this.totalPages = data.totalPages;
            this.pageNumber = data.pageable.pageNumber;
            this.size = data.size;
            this.page = data.pageable.pageNumber;
            this.message = '';
            // console.log(this.message);
          } else {
            this.message = 'Không tìm thấy';
            this.realEstateNews = [];
            this.totalPages = 0;
          }
          this.flag = true;
        });
      }

    }
  }

  previousClick(index) {
    this.page = this.page - index;
    this.ngOnInit();
  }

  nextClick(index) {
    this.page = this.page + index;
    console.log('next pay ' + this.page);
    this.ngOnInit();
  }

  findPaginnation(value: number) {
    this.page = value - 1;
    this.ngOnInit();
  }

  onsubmit() {
    this.flag = false;
    this.search();
  }

  // 5.7.1 Duyệt/Xóa bài đăng..Method Duyệt bài đăng
  // acceptApproval(id) {
  //   this.realService.findRealEstateNewById(id).subscribe(data => {
  //     console.log(id);
  //     this.realEstateNew = data;
  //     console.log(this.realEstateNew.customer.email);
  //     this.customerEmail = this.realEstateNew.customer.email;
  //     this.emailForm = new FormGroup({
  //       status: new FormControl('Đã được duyệt'),
  //       reason: new FormControl(''),
  //       customerEmail: new FormControl(this.customerEmail)
  //     });
  //     // this.approvalMail.customerEmail = this.realEstateNew.customer.email;
  //     this.realService.acceptApprove(this.realEstateNew, id).subscribe(dataApproval => {
  //       console.log(this.emailForm.value);
  //       this.realService.sendApprovalMail(this.emailForm.value).subscribe();
  //       this.ngOnInit();
  //       alert('Duyệt bài thành công');
  //     });
  //   });
  // }

  // 5.7.1 Duyệt/Xóa bài đăng..Method Không Duyệt bài đăng
  // dontAcceptApproval(id) {
  //   this.realService.findRealEstateNewById(id).subscribe(data => {
  //     console.log(id);
  //     this.realEstateNew = data;
  //     console.log(this.realEstateNew.customer.email);
  //     this.customerEmail = this.realEstateNew.customer.email;
  //     this.emailForm = this.form.group({
  //       status: new FormControl('Không được duyệt'),
  //       reason: new FormControl(''),
  //       customerEmail: new FormControl(this.customerEmail)
  //     });
  //     // this.approvalMail.customerEmail = this.realEstateNew.customer.email;
  //     this.realService.dontAcceptApprove(this.approvalMail).subscribe(dataApproval => {
  //       console.log(this.emailForm.value);
  //       this.realService.sendApprovalMail(this.emailForm.value).subscribe();
  //       this.ngOnInit();
  //     });
  //   });
  // }

  getInfo() {
    this.realService.sendApprovalMail(this.emailForm.value).subscribe();
  }

  // 5.7.1 DoanhNV - Dialog nay goi khi Duyet
  openDialogAccept(id) {
    console.log(id);
    const dialogRef = this.dialog.open(DetailPostApprovalComponent, {
      data: {id},
      width: '600px',
      panelClass: 'custom-dialog-tai',
      // Khi bấm ra ngoài dialog khong bi mat di
      disableClose: true
    });
    console.log('abc');
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.ngOnInit();
    });
  }

  // 5.7.1 DoanhNV - Dialog nay duoc goi khi Khong duyet bai dang
  openDialog(id) {
    console.log(id);
    const dialogRef = this.dialog.open(DeletePostApprovalComponent, {
      data: {id},
      width: '600px',
      panelClass: 'custom-dialog-tai',
      // Khi bấm ra ngoài dialog khong bi mat di
      disableClose: true
    });
    console.log('abc');
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.ngOnInit();
    });
  }
}
