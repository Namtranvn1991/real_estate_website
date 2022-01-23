import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {RealRoutingModule} from './real-routing.module';
import {RealListComponent} from './real-list/real-list.component';
import {RealDeleteComponent} from './real-delete/real-delete.component';
import {RealDetailComponent} from './real-detail/real-detail.component';
import {HistoryPostComponent} from './history-post/history-post.component';
import {DetailPostApprovalComponent} from './detail-post-approval/detail-post-approval.component';
import {DeletePostApprovalComponent} from './delete-post-approval/delete-post-approval.component';
import {ListPostApprovalComponent} from './list-post-approval/list-post-approval.component';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {EmailComponent} from './email/email.component';
import {RealCreateComponent} from './real-create/real-create.component';
import {MatInputModule} from '@angular/material/input';


@NgModule({
  declarations: [RealListComponent,
    RealDeleteComponent,
    RealDetailComponent,
    HistoryPostComponent,
    DetailPostApprovalComponent,
    DeletePostApprovalComponent,
    ListPostApprovalComponent,
    RealCreateComponent,
    EmailComponent],
  exports: [
    RealCreateComponent,
    RealDeleteComponent,
    RealDetailComponent,
    HistoryPostComponent,
    DetailPostApprovalComponent,
    DeletePostApprovalComponent,
    ListPostApprovalComponent,
    RealCreateComponent],
    imports: [
        CommonModule,
        RealRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        MatInputModule
    ]
})

export class RealModule {
}
