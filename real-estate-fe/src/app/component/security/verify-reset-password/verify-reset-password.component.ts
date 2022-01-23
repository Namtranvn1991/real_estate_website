import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../service/auth.service';

export function checkPasswordAndRePassword(c: AbstractControl) {
  const v = c.value;
  const newPassWord = v.newPassword;
  const reNewPassWord = v.reNewPassword;

  return (newPassWord === reNewPassWord) ? null : {checkpasswordandrepassword: true};
}

@Component({
  selector: 'app-verify-reset-password',
  templateUrl: './verify-reset-password.component.html',
  styleUrls: ['./verify-reset-password.component.scss']
})
export class VerifyResetPasswordComponent implements OnInit {
  isExpiredCode: boolean;
  verificationCode: string;
  resetForm: FormGroup;
  messageSuccess = '';
  messageError = '';

  constructor(private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.resetForm = this.formBuilder.group({
      newPassword: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,12}$')]],
      reNewPassword: ['', [Validators.required]]
    }, {
      validators: checkPasswordAndRePassword
    });
    this.activatedRoute.queryParams.subscribe(params => {
      this.verificationCode = params['code'];
      this.authService.checkVerificationCode(this.verificationCode).subscribe(data => {
        this.isExpiredCode = data.message === 'valid';
      });
    });
  }

  onSubmitt() {
    if (this.resetForm.valid) {
      this.authService.resetPassword(this.resetForm.value.newPassword, this.resetForm.value.reNewPassword,
        this.verificationCode).subscribe(data => {
        this.messageSuccess = 'Cập nhập mật khẩu thành công!';
      }, error => {
        if (error.message === 'error') {
          this.messageError = 'Nhập lại mật khẩu không giống mật khẩu';
        }

      });
    }
  }
}
