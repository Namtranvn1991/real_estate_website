import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {EmployeeListComponent} from './employee-list/employee-list.component';
import {EmployeeCreateComponent} from './employee-create/employee-create.component';
import {EmployeeEditComponent} from './employee-edit/employee-edit.component';
import {EmployeeDeleteComponent} from './employee-delete/employee-delete.component';
import {AuthGuard} from '../../helpers/auth.guard';

const routes: Routes = [
  {
    path: 'list',
    component: EmployeeListComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN']}
  },
  {
    path: 'create', component: EmployeeCreateComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN']}
  },
  {
    path: 'edit/:id', component: EmployeeEditComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN']}
  },
  {
    path: 'delete/:id', component: EmployeeDeleteComponent, canActivate: [AuthGuard],
    data: {expectedRole: ['ROLE_ADMIN']}
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule {
}
