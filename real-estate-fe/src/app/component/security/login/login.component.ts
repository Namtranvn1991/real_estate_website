import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {SocialAuthService, SocialUser} from 'angularx-social-login';
import {FacebookLoginProvider, GoogleLoginProvider} from 'angularx-social-login';
import {AuthService} from '../../../service/auth.service';
import {TokenStorageService} from '../../../service/token-storage.service';
import {ResetPasswordComponent} from '../reset-password/reset-password.component';
import {ShareService} from '../../../service/share.service';
import {CustomerCreateComponent} from '../../customer/customer-create/customer-create.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  userForm: FormGroup;
  username: string;
  roles: string[] = [];
  returnUrl: string;
  errorMessage = '';

  socialUser: SocialUser;
  userLogged: SocialUser;

  constructor(
    private dialogRef: MatDialogRef<LoginComponent>,
    private dialog: MatDialog,
    private fb: FormBuilder,
    private authService: AuthService,
    private tokenStorageService: TokenStorageService,
    private router: Router,
    private route: ActivatedRoute,
    private socialAuthService: SocialAuthService,
    private shareService: ShareService
  ) {
  }

  ngOnInit(): void {

    this.userForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
      remember_me: false
    });

    if (this.tokenStorageService.getUser()) {
      this.authService.isLoggedIn = true;
      this.roles = this.tokenStorageService.getUser().roles;
      this.username = this.tokenStorageService.getUser().username;
    }
    this.socialAuthService.authState.subscribe(
      data => {
        this.userLogged = data;
        this.authService.isLoggedIn = (this.userLogged != null && this.tokenStorageService.getUser() != null);
      }
    );
  }

  onSubmit() {
    this.authService.login(this.userForm.value).subscribe(data => {
      if (this.userForm.value.remember_me === true) {
        this.tokenStorageService.saveUserLocal(data);
        this.tokenStorageService.saveTokenLocal(data.jwtToken);
      } else {
        this.tokenStorageService.saveUserSession(data);
        this.tokenStorageService.saveTokenSession(data.jwtToken);
      }
      this.authService.isLoggedIn = true;
      this.username = this.tokenStorageService.getUser().username;
      this.roles = this.tokenStorageService.getUser().roles;
      console.log('username: ' + this.tokenStorageService.getUser().username);
      console.log('role: ' + this.tokenStorageService.getUser().roles);
      console.log('token: ' + this.tokenStorageService.getUser().jwtToken);
      this.userForm.reset();
      this.dialogRef.close();
      if (this.roles.indexOf('ROLE_CUSTOMER') !== -1) {
        this.router.navigate(['/real-estate-new/list']);
        this.shareService.sendClickEvent();
      } else if (this.roles.indexOf('ROLE_EMPLOYEE') !== -1) {
        this.router.navigate(['customer/list']);
        this.shareService.sendClickEvent();
      } else {
        this.router.navigate(['employee/list']);
        this.shareService.sendClickEvent();
      }
    }, error => {
      console.log(error);
      this.authService.isLoggedIn = false;
      this.errorMessage = 'Tài khoản hoặc mật khẩu không đúng!';
    });
  }

  closeDialog() {
    this.dialogRef.close();
  }

  openDialogResetPassword() {
    this.dialogRef.close();
    const dialogResetPassword = this.dialog.open(ResetPasswordComponent, {
      width: '450px',
      height: '300px',
      panelClass: 'custom-dialog',
      disableClose: true
    });

    dialogResetPassword.afterClosed().subscribe(result => {
    });
  }

  signInWithGoogle(): void {
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(
      data => {
        this.socialUser = data;
        console.log(this.socialUser);
        this.authService.google(this.socialUser.idToken).subscribe(
          res => {
            this.tokenStorageService.saveUserSession(res);
            this.tokenStorageService.saveTokenSession(res.jwtToken);

            console.log('username: ' + this.tokenStorageService.getUser().username);
            console.log('role: ' + this.tokenStorageService.getUser().roles);
            console.log('token:' + this.tokenStorageService.getToken());
            this.authService.isLoggedIn = true;
            this.dialogRef.close();
            this.router.navigate(['/home']);
            this.shareService.sendClickEvent();

          },
          err => {
            console.log(err);
            this.logOut();
          }
        );
      }
    ).catch(
      err => {
        console.log(err);
      }
    );
  }

  signInWithFB(): void {
    this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID).then(
      data => {
        this.socialUser = data;
        console.log(this.socialUser);
        this.authService.facebook(this.socialUser.authToken).subscribe(
          res => {
            this.tokenStorageService.saveUserSession(res);
            this.tokenStorageService.saveTokenSession(res.jwtToken);

            console.log('username: ' + this.tokenStorageService.getUser().username);
            console.log('role: ' + this.tokenStorageService.getUser().roles);
            console.log('token:' + this.tokenStorageService.getToken());
            this.authService.isLoggedIn = true;
            this.dialogRef.close();
            this.router.navigate(['/home']);
            this.shareService.sendClickEvent();

          },
          err => {
            console.log(err);
            this.logOut();
          }
        );
      }
    ).catch(
      err => {
        console.log(err);
      }
    );
  }

  logOut(): void {
    this.socialAuthService.signOut().then(
      data => {
        this.tokenStorageService.signOut();
        this.authService.isLoggedIn = false;
      }
    );
  }

  openDialogRegister() {
    this.dialogRef.close();
    const dialogRegister = this.dialog.open(CustomerCreateComponent, {
      width: '450px',
      panelClass: 'custom-dialog-create-customer',
      disableClose: true
    });

    dialogRegister.afterClosed().subscribe(result => {
    });
  }
}
