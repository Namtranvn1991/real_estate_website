import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CustomerListComponent} from './customer-list/customer-list.component';
import {CustomerDeleteComponent} from './customer-delete/customer-delete.component';
import {CustomerCreateComponent} from './customer-create/customer-create.component';
import {CustomerEditComponent} from './customer-edit/customer-edit.component';
import {CustomerDetailComponent} from './customer-detail/customer-detail.component';
import {UpdatePasswordComponent} from './update-password/update-password.component';
import {AuthGuard} from '../../helpers/auth.guard';


const routes: Routes = [
  {
    path: 'list', component: CustomerListComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN', 'ROLE_EMPLOYEE']}
  },
  {path: 'detail/:id', component: CustomerDetailComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CUSTOMER']}
  },
  {path: 'delete', component: CustomerDeleteComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN', 'ROLE_EMPLOYEE']}
  },
  {path: 'create', component: CustomerCreateComponent
  },
  {path: 'edit', component: CustomerEditComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CUSTOMER']}
  },
  {path: 'change-password', component: UpdatePasswordComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CUSTOMER']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule {
}
