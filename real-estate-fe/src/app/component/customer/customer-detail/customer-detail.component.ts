import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {CustomerService} from '../../../service/customer.service';
import {Customer} from '../../../model/customer/customer';

@Component({
  selector: 'app-customer-detail',
  templateUrl: './customer-detail.component.html',
  styleUrls: ['./customer-detail.component.scss']
})
export class CustomerDetailComponent implements OnInit {
  public customer: Customer;
  private subscription: Subscription;
  id: string;


  constructor(private customerService: CustomerService,
              private activatedRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.id = params.get('id');
      console.log(this.id);
      this.customerService.findCustomerById(this.id).subscribe(
        data => {
          console.log(data);
          this.customer = data;
        }, error => {
          console.log('error');
        }
      );
    });
    // this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {
    //   this.id = paramMap.get('id');
    //   console.log(this.id);
    // });
    // this.customerService.findCustomerById(this.id).subscribe(data => {
    //   console.log(this.id);
    //   this.customer = data;
    //   console.log(this.customer);
    // }, error => {
    //   console.log(error);
    // });
  }
}
