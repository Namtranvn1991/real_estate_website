import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {CustomerService} from '../../../service/customer.service';

@Component({
  selector: 'app-customer-delete',
  templateUrl: './customer-delete.component.html',
  styleUrls: ['./customer-delete.component.scss']
})
export class CustomerDeleteComponent implements OnInit {
  public customerName;
  public customerId;
  constructor(
    public dialogRef: MatDialogRef<CustomerDeleteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private customerService: CustomerService
  ) { }

  ngOnInit(): void {
    this.customerName = this.data.data2.name;
    this.customerId = this.data.data2.id;
  }

  deleteCustomer() {
    this.customerService.deleteCustomer(this.customerId).subscribe(data => {

      this.dialogRef.close();
    });
  }
}
