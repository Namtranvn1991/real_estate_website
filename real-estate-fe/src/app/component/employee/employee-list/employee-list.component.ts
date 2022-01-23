import {Component, OnInit} from '@angular/core';
import {Employee} from '../../../model/employee/employee';
import {EmployeeService} from '../../../service/employee.service';
import {EmployeeDeleteComponent} from '../employee-delete/employee-delete.component';
import {MatDialog} from '@angular/material/dialog';
import {Observable, Subscription} from 'rxjs';
import {PositionService} from '../../../service/position.service';
import {Positions} from '../../../model/employee/positions';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[];
  private subscription: Subscription | undefined;
  page = 0;
  employeeName = '';
  employeeEmail = '';
  employeePosition = '';
  totalPages: number;
  pageNumber: number;
  size = 0;
  flag = false;
  message: string;
  positionList1: Observable<Positions[]>;
  positionList: Positions[];

  constructor(private employeeService: EmployeeService,
              private dialogDelete: MatDialog,
              private positionService: PositionService) {
  }

  ngOnInit(): void {
    this.positionList1 = this.positionService.getAllPosition();
    this.positionList1.subscribe(data => {
      this.positionList = data;
    });
    this.search();
  }

  search() {
    if (this.employeeName === '' && this.employeeEmail === '' && this.employeePosition === '') {
      this.flag = false;
      this.employeeService.search(this.page, this.employeeName, this.employeeEmail, this.employeePosition).subscribe(data => {
        console.log(data);
        if (data !== null) {
          this.employees = data.content;
          this.totalPages = data.totalPages;
          this.pageNumber = data.pageable.pageNumber;
          this.size = data.size;
          this.page = data.pageable.pageNumber;
          this.message = '';
        } else {
          this.message = 'Không tìm thấy';
          this.employees = [];
          this.totalPages = 0;
        }
      });
    } else {
      if (this.flag === false) {
        this.page = 0;
        this.employeeService.search(this.page, this.employeeName, this.employeeEmail, this.employeePosition).subscribe(data => {
          if (data !== null) {
            this.employees = data.content;
            this.totalPages = data.totalPages;
            this.pageNumber = data.pageable.pageNumber;
            this.size = data.size;
            this.page = data.pageable.pageNumber;
            this.message = '';
          } else {
            this.message = 'Không tìm thấy';
            this.employees = [];
            this.totalPages = 0;
          }
          this.flag = true;
        });
      } else {
        this.employeeService.search(this.page, this.employeeName, this.employeeEmail, this.employeePosition).subscribe(data => {
          if (data !== null) {
            this.employees = data.content;
            this.totalPages = data.totalPages;
            this.pageNumber = data.pageable.pageNumber;
            this.size = data.size;
            this.page = data.pageable.pageNumber;
            this.message = '';
            console.log(this.message);
          } else {
            this.message = 'Không tìm thấy';
            this.employees = [];
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

  openDialog(employeeId: string) {
    console.log(employeeId);
    this.employeeService.getEmployee(employeeId).subscribe(customerData => {
      const dialogRef = this.dialogDelete.open(EmployeeDeleteComponent, {
        width: '500px',
        panelClass: 'custom-dialog-tai',
        data: {customerData},
        disableClose: true
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
        this.ngOnInit();
      });
    });
  }
}
