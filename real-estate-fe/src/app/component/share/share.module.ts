import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShareRoutingModule } from './share-routing.module';
import { AsideComponent } from './aside/aside.component';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [ AsideComponent],
  exports: [
  ],
  imports: [
    CommonModule,
    ShareRoutingModule,
    ReactiveFormsModule
  ]
})
export class ShareModule { }
