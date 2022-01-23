import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialogRef} from '@angular/material/dialog';
import {AuthService} from '../../../service/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  userForm: FormGroup;
  messageSuccess: string;
  messageError: string;
  isSubmit = false;

  constructor(public dialogResetPassword: MatDialogRef<ResetPasswordComponent>,
              private formBuilder: FormBuilder,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.userForm = this.formBuilder.group({
      email: ['', Validators.required]
    });
  }

  closeDialog() {
    this.dialogResetPassword.close();
  }

  onSubmit() {
    this.isSubmit = true;
    this.authService.requestResetPassword(this.userForm.value.email).subscribe(
      data => {
        this.isSubmit = false;
        this.messageSuccess = 'Đã gửi Email xác nhận.';
        this.messageError = '';
      },
      err => {
        this.isSubmit = false;
        // @ts-ignore
        this.messageError = 'Email của bạn không tồn tại trong hệ thống. Bạn vui lòng điền đúng Email đã đăng kí ' +
          '(hoặc liên hệ với chúng tôi để được giúp đỡ)';

      }
    );
  }
}
