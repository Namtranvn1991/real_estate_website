import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {RealService} from '../../../service/real.service';
import {Subscription} from 'rxjs';
import {Email} from '../../../model/real/email';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RealEstateNew} from '../../../model/real/real-estate-new';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.scss']
})
export class EmailComponent implements OnInit {
  email: Email;
  customerMail: string;
  id: string;
  private subscription: Subscription;
  realEstate: RealEstateNew;
  // formInfo = new FormGroup({
  //   name: new FormControl(),
  //   phone: new FormControl()
  // });
  public formInfo: FormGroup;

  constructor(
    private realService: RealService,
    public dialogRef: MatDialogRef<EmailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    console.log(this.data.id);
    this.id = this.data.id;
    this.subscription = this.realService.findRealEstateNewById(this.data.id).subscribe(dataRealEstate => {
      console.log(dataRealEstate);
      this.customerMail = dataRealEstate.customer.email;
    });
    this.formInfo = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(3)]),
      // tslint:disable-next-line:max-line-length
      phone: new FormControl('', [Validators.required, Validators.pattern('^(0?)(3[2-9]|5[6|89]|7[0|6-9]|8[0-6|89]|9[0-4|6-9])[0-9]{7}$')]),
      customerMail: new FormControl(this.customerMail)
    });
    console.log(this.formInfo.value);
  }

  onSubmit() {
    console.log(this.formInfo.value);
    if (this.formInfo.valid) {
      this.subscription = this.realService.sendMail(this.formInfo.value).subscribe(data => {
      });
      this.dialogRef.close();
    }
  }

  get name() {
    return this.formInfo.get('name');
  }

  get phone() {
    return this.formInfo.get('phone');
  }
}
