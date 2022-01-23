import {Component, Inject, OnInit} from '@angular/core';
import {Observable, Subscription} from 'rxjs';
import {Employee} from '../../../model/employee/employee';
import {ActivatedRoute, Router} from '@angular/router';
import {EmployeeService} from '../../../service/employee.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-employee-delete',
  templateUrl: './employee-delete.component.html',
  styleUrls: ['./employee-delete.component.scss']
})
export class EmployeeDeleteComponent implements OnInit {
  id: number;
  employee: Employee;
  private subscription: Subscription;

  constructor(
    private medicalService: EmployeeService,
    public dialogRef: MatDialogRef<EmployeeDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    console.log(this.data.customerData.id);
    this.employee = this.data.customerData;
  }

  deleteMedical() {
    console.log(this.employee.id);
    this.subscription = this.medicalService.deleteEmployeeById(this.employee.id).subscribe(data => {
      this.dialogRef.close();
    });
  }

}
