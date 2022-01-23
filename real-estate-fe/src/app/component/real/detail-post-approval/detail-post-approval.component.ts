import {Component, Inject, OnInit} from '@angular/core';
import {ApprovalMail} from '../../../model/real/approval-mail';
import {FormControl, FormGroup} from '@angular/forms';
import {Subscription} from 'rxjs';
import {RealEstateNew} from '../../../model/real/real-estate-new';
import {RealService} from '../../../service/real.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';


@Component({
  selector: 'app-detail-post-approval',
  templateUrl: './detail-post-approval.component.html',
  styleUrls: ['./detail-post-approval.component.scss']
})
export class DetailPostApprovalComponent implements OnInit {
  realEstateNews: RealEstateNew[];
  realEstateNew: RealEstateNew;
  realForm: FormGroup;
  emailForm: FormGroup;
  customerEmail: string;
  approvalMail: ApprovalMail;
  public formInfo: FormGroup;
  private subscription: Subscription;
  private realEstate: RealEstateNew;
  id: string;

  constructor(
              private realService: RealService,
              public dialogRef: MatDialogRef<DetailPostApprovalComponent>,
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
      this.realEstate = dataRealEstate;
      this.customerEmail = dataRealEstate.customer.email;
    });
    console.log(this.data);
    this.realEstate = this.data;
    this.formInfo = new FormGroup({
      status: new FormControl('Không được duyệt vì lý do : '),
      reason: new FormControl(''),
      customerEmail: new FormControl(this.customerEmail)
    });
    console.log(this.formInfo.value);
  }


  onSubmit() {
    console.log(this.formInfo.value);
    this.subscription = this.realService.dontAcceptApprove(this.realEstate, this.id).subscribe(data => {
    });
    this.subscription = this.realService.sendApprovalMail(this.formInfo.value).subscribe(data => {
    });
    this.dialogRef.close();
    this.ngOnInit();
  }

  // 5.7.1 Duyệt/Xóa bài đăng..Method Duyệt bài đăng
  acceptApproval(id) {
    this.realService.findRealEstateNewById(id).subscribe(data => {
      console.log(id);
      this.realEstateNew = data;
      console.log(this.realEstateNew.customer.email);
      this.customerEmail = this.realEstateNew.customer.email;
      this.emailForm = new FormGroup({
        status: new FormControl('Đã được duyệt'),
        reason: new FormControl(''),
        customerEmail: new FormControl(this.customerEmail)
      });
      // this.approvalMail.customerEmail = this.realEstateNew.customer.email;
      this.realService.acceptApprove(this.realEstateNew, id).subscribe(dataApproval => {
        console.log(this.emailForm.value);
        this.realService.sendApprovalMail(this.emailForm.value).subscribe();
        this.dialogRef.close();
        this.ngOnInit();
      });
    });
  }

}
