import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {AppUser} from '../../../model/user/app-user';
import {CustomerService} from '../../../service/customer.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TokenStorageService} from '../../../service/token-storage.service';

export function checkPasswordAndRePassword(c: AbstractControl) {
  const v = c.value;
  const newPassWord = v.newPassword;
  const reNewPassWord = v.reNewPassword;

  return (newPassWord === reNewPassWord) ? null : {checkpasswordandrepassword: true};
}

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.scss']
})
export class UpdatePasswordComponent implements OnInit {
  appUserForm: FormGroup;
  usernameChange: string;
  message = '';

  constructor(private customerService: CustomerService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.usernameChange = this.tokenStorageService.getUser().username;
    this.initFormEdit();
  }

  updatePassword(): void {
    this.customerService.changePassword(this.appUserForm.value).subscribe(value => {
        this.message = 'đã thay đổi mật khẩu thành công';
      },
      error => {
        this.message = 'Cập nhật thất bại';
      });
  }

  onClick() {
    this.appUserForm.reset();
  }
  initFormEdit() {
    this.appUserForm = new FormGroup({
      usernameChange: new FormControl(this.usernameChange, [Validators.required]),
      password: new FormControl('', [Validators.required]),
      newPassword: new FormControl('', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/)]),
      reNewPassword: new FormControl('', [Validators.required])
    }, {validators: checkPasswordAndRePassword});
  }
}
