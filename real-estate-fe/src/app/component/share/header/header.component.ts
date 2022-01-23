import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {TokenStorageService} from '../../../service/token-storage.service';
import {Router} from '@angular/router';
import {LoginComponent} from '../../security/login/login.component';
import {ShareService} from '../../../service/share.service';
import {CustomerCreateComponent} from '../../customer/customer-create/customer-create.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  username: string;
  role: string;
  urlImg: string;
  isLoggedIn: boolean;
  idCustomer: string;

  constructor(public dialog: MatDialog,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private shareService: ShareService) {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
  }

  ngOnInit(): void {
    this.loadHeader();
  }

  loadHeader(): void {
    if (this.tokenStorageService.getUser()) {
      this.role = this.tokenStorageService.getUser().roles[0];
      this.username = this.tokenStorageService.getUser().username;
      this.urlImg = this.tokenStorageService.getUser().urlImg;
      this.idCustomer = this.tokenStorageService.getUser().idCustomer;
    } else {
      this.role = null;
      this.username = null;
      this.urlImg = null;
      this.idCustomer = null;
    }
    this.isLoggedIn = this.username != null;
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(LoginComponent, {
      width: '450px',
      panelClass: 'custom-dialog',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  logout() {
    this.tokenStorageService.signOut();
    this.loadHeader();
    this.router.navigate(['/home']);
  }

  openDialogRegister() {
    const dialogRef = this.dialog.open(CustomerCreateComponent, {
      width: '550px',
      maxHeight: '100%',
      panelClass: 'custom-dialog-create-customer',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }
}

// login() {
//   this.dialogRef.close();
//   const dialogLogin = this.dialog.open(LoginComponent, {
//     width: '450px',
//     panelClass: 'custom-dialog',
//     disableClose: true
//   });
//
