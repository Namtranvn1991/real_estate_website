import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AccessDeniedComponent} from './access-denied/access-denied.component';
import {VerifyResetPasswordComponent} from './verify-reset-password/verify-reset-password.component';


const routes: Routes = [
  {path: 'access-denied', component: AccessDeniedComponent},
  {path: 'verify-reset-password', component: VerifyResetPasswordComponent}
  ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SecurityRoutingModule {
}
