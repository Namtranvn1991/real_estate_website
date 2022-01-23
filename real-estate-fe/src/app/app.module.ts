import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {CustomerModule} from './component/customer/customer.module';
import {RealModule} from './component/real/real.module';
import {SecurityModule} from './component/security/security.module';
import {ShareModule} from './component/share/share.module';
import {EmployeeModule} from './component/employee/employee.module';
import {HeaderComponent} from './component/share/header/header.component';
import {FooterComponent} from './component/share/footer/footer.component';

import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {SocialLoginModule, SocialAuthServiceConfig} from 'angularx-social-login';
import {GoogleLoginProvider, FacebookLoginProvider} from 'angularx-social-login';
import {authInterceptorProviders} from './helpers/auth.interceptor';
import {JWT_OPTIONS, JwtHelperService} from '@auth0/angular-jwt';
import {environment} from '../environments/environment';
import {AngularFirestoreModule} from '@angular/fire/firestore';
import {AngularFireAuthModule} from '@angular/fire/auth';
import {AngularFireDatabaseModule} from '@angular/fire/database';
import {AngularFireStorageModule} from '@angular/fire/storage';
import {AngularFireMessagingModule} from '@angular/fire/messaging';
import {AngularFireFunctionsModule} from '@angular/fire/functions';
import {AngularFireModule} from '@angular/fire';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import {BottomSheetNotifyComponent} from './component/util/bottom-sheet-notify/bottom-sheet-notify.component';
import {MatDialogModule, MatDialogRef} from '@angular/material/dialog';
import {APP_BASE_HREF} from '@angular/common';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';


@NgModule({
  declarations: [
    AppComponent,
    BottomSheetNotifyComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    SecurityModule,
    RealModule,
    ShareModule,
    CustomerModule,
    EmployeeModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFirestoreModule,
    AngularFireAuthModule,
    AngularFireStorageModule,
    AngularFireMessagingModule,
    AngularFireDatabaseModule,
    AngularFireFunctionsModule,
    MatButtonModule,
    MatBottomSheetModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    AppRoutingModule,
    MatDialogModule,
    SocialLoginModule,
  ],
  providers: [{
    provide: 'SocialAuthServiceConfig',
    useValue: {
      autoLogin: false,
      providers: [
        {
          id: GoogleLoginProvider.PROVIDER_ID,
          provider: new GoogleLoginProvider(
            '298834683029-khkrl7c9u4csbtokm3vbl5ijcc5i6eun.apps.googleusercontent.com'
          )
        },
        {
          id: FacebookLoginProvider.PROVIDER_ID,
          provider: new FacebookLoginProvider('636920287629670')
        }
      ]
    } as SocialAuthServiceConfig,
  }, authInterceptorProviders,
    {provide: JWT_OPTIONS, useValue: JWT_OPTIONS},
    JwtHelperService, {provide: APP_BASE_HREF, useValue: '/'}],
  entryComponents: [BottomSheetNotifyComponent],
  exports: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
