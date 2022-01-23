import {Component, OnInit} from '@angular/core';
import {Customer} from '../../../model/customer/customer';
import {Subscription} from 'rxjs';
import {CustomerService} from '../../../service/customer.service';
import {MatDialog} from '@angular/material/dialog';
import {CustomerDeleteComponent} from '../customer-delete/customer-delete.component';

// import {ToastrService} from 'ngx-toastr';


@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.scss']
})
export class CustomerListComponent implements OnInit {

  constructor(
    private customerService: CustomerService,
    private dialog: MatDialog,
  ) {
  }

  customers: Customer[] = [];
  private subscription: Subscription | undefined;

  page = 0;
  customerName = '';
  customerPhone = '';
  customerEmail = '';
  totalPages: number;
  pageNumber: number;
  size = 0;
  flagSearch = false;
  message: string;



  clickPage1 = false;
  clickPage2 = false;
  buttonDisabled = false;

  ngOnInit(): void {
    this.showCustomer();
  }

  onSubmit() {
    this.flagSearch = false;
    this.showCustomer();
  }

  showCustomer() {
    if (this.customerName === '' && this.customerPhone === '' && this.customerEmail === '') {
      this.flagSearch = false;
      this.getCustomer();
    } else if (this.customerName !== '' && this.customerPhone === '' && this.customerEmail === '') {
      this.searchCustomer();
    } else if (this.customerName !== '' && this.customerPhone !== '' && this.customerEmail === '') {
      this.searchCustomer();
    } else if (this.customerName !== '' && this.customerPhone !== '' && this.customerEmail !== '') {
      this.searchCustomer();
    } else if (this.customerName === '' && this.customerPhone !== '' && this.customerEmail === '') {
      this.searchCustomer();
    } else if (this.customerName === '' && this.customerPhone !== '' && this.customerEmail !== '') {
      this.searchCustomer();
    } else if (this.customerName === '' && this.customerPhone === '' && this.customerEmail !== '') {
      this.searchCustomer();
    } else if (this.customerName !== '' && this.customerPhone === '' && this.customerEmail !== '') {
      this.searchCustomer();
    }
  }

  getCustomer() {
    this.customerService.findCustomer(this.page, this.customerName, this.customerPhone, this.customerEmail).subscribe(data => {
      // console.log(data);
      if (data !== null) {
        this.customers = data.content;
        this.totalPages = data.totalPages;
        this.pageNumber = data.pageable.pageNumber;
        this.size = data.size;
        this.page = data.pageable.pageNumber;
        this.message = '';
        console.log(this.message);
      } else {
        this.message = 'Not found !!!';
        console.log(this.message);
      }
    });
  }

  searchCustomer() {
    if (this.flagSearch === false) {
      this.page = 0;
      this.getCustomer();
      this.flagSearch = true;
    } else {
      this.getCustomer();
      this.flagSearch = true;
    }
  }

  openDialog(customerId) {
    this.customerService.getCustomerById(customerId).subscribe(data => {
      const dialogRef = this.dialog.open(CustomerDeleteComponent, {
        width: '500px',
        data: {data2: data},
        disableClose: true
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log('the dialog was closed');
        // this.showAlert();
        this.ngOnInit();
      });
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
}
