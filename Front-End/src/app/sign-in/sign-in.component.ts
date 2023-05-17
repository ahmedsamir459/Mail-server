import { HttpClient } from '@angular/common/http';
import { Component, NgModule, OnInit } from '@angular/core';
import { ApiserveService } from '../service';
import { Router } from '@angular/router';
import { CurrentuseService } from '../currentuse.service';
@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
valid=true
constructor(private service:ApiserveService,private http:HttpClient,private router:Router,private userservice:CurrentuseService) { }

  ngOnInit(): void {
  }
  sign_in(mail:any,pass:any){
    if(mail.length==0||pass.length==0){
      alert('enter email and password');
    }
    else{
    if(this.checkEmail(mail)==false){
    alert('email cannot can only contain letters ,numbers,_+@mail.com')
    }
    else{
      this.service.sign_in(mail,pass).subscribe(res=>{
         if(res=='not found'){
           alert('email or password is wrong, try again')
         }
         else{
          this.userservice.currentuser=mail
           this.router.navigate(['/homepage']);
         }
        console.log(res)
      })

    }
  }
}
  checkEmail(mail2:any){
    for (let i = 0; i < mail2.length; i++) {
      if(!this.charIsLetter(mail2[i])&&!this.charIsNumber(mail2[i])&&mail2[i]!='@'&&mail2[i]!='.'&&mail2[i]!='_'){
    this.valid=false
      }
      }
      var valid1=this.valid;
      this.valid=true;
      return valid1
  }
  charIsLetter(char:any) {
    if (typeof char !== 'string') {
      return false;
    }
  
    return /^[a-zA-Z]+$/.test(char);
  }
  charIsNumber(char:any) {
    return /^\d$/.test(char);
  }
}
