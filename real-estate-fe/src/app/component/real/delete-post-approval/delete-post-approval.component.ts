import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {RealService} from '../../../service/real.service';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {RealEstateNew} from '../../../model/real/real-estate-new';
import {ApprovalMail} from '../../../model/real/approval-mail';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-delete-post-approval',
  templateUrl: './delete-post-approval.component.html',
  styleUrls: ['./delete-post-approval.component.scss']
})
export class DeletePostApprovalComponent implements OnInit {

  customerEmail: string;
  approvalMail: ApprovalMail;
  public formInfo: FormGroup;
  private subscription: Subscription;
  private realEstate: RealEstateNew;
  id: string;

  constructor(
    private realService: RealService,
    public dialogRef: MatDialogRef<DeletePostApprovalComponent>,
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
}


