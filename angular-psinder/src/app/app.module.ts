import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { PetListComponent } from './components/pet-list/pet-list.component';
import { Routes, RouterModule, Router } from '@angular/router';
import { PetService } from './services/pet.service';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { LogoutComponent } from './components/logout/logout.component';
import { AuthGuardService } from './services/auth-guard.service';
import { SignupComponent } from './components/signup/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UserPageComponent } from './components/user-page/user-page.component';
import { ConnectionChooseComponent } from './components/connection-choose/connection-choose.component';
import { ConnectionListWalkerComponent } from './components/connection-list-walker/connection-list-walker.component';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { ConnectionWalkerComponent } from './components/connection-walker/connection-walker.component';

const routes: Routes = [
  {path: 'home', component: WelcomePageComponent},
  {path: 'connections-walker/search/:keyword', component: ConnectionListWalkerComponent, canActivate:[AuthGuardService]},
  {path: 'connections-walker', component: ConnectionListWalkerComponent, canActivate:[AuthGuardService]},
  {path: 'connection-walker/:id', component: ConnectionWalkerComponent, canActivate:[AuthGuardService]},
  {path: 'choose-connection', component: ConnectionChooseComponent, canActivate:[AuthGuardService]},
  {path: 'profile', component: UserPageComponent, canActivate:[AuthGuardService]},
  {path: 'signup', component: SignupComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'pets', component: PetListComponent, canActivate:[AuthGuardService]},
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: '**', redirectTo: '/home', pathMatch: 'full'},
];

@NgModule({
  declarations: [
    AppComponent,
    PetListComponent,
    LoginComponent,
    LogoutComponent,
    SignupComponent,
    UserPageComponent,
    ConnectionChooseComponent,
    ConnectionListWalkerComponent,
    WelcomePageComponent,
    ConnectionWalkerComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [PetService, {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }

