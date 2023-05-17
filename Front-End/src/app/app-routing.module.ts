import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComposeComponent } from './compose/compose.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignupComponent } from './signup/signup.component';
import { HomepageComponent } from './homepage/homepage.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ContactComponent } from './contact/contact.component';
import { EmailComponent } from './email/email.component';
const routes: Routes = [
  {path:'compose',component:ComposeComponent},
   {path:'SignIn',component:SignInComponent},
   {path:'signup',component:SignupComponent},
   {path:'homepage',component:HomepageComponent},
   {path:'sidebar',component:SidebarComponent},
   {path:'Contacts',component:ContactComponent},
  {path:'Mail',component:EmailComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents=[SignInComponent,ComposeComponent,SignupComponent,HomepageComponent,SidebarComponent,ContactComponent,EmailComponent];