import { HttpClient } from '@angular/common/http';
import { Component, NgModule, OnInit } from '@angular/core';
import { ApiserveService } from '../service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {


  constructor(private service:ApiserveService,private http:HttpClient,private router:Router) { }

  ngOnInit(): void {
  }

 valid=true;

signup(mail1:any,pass1:any){
if(this.checkEmail(mail1)==false){
alert('email can only consists of numbers or letter or _');
}
else{
if(pass1.length==0||mail1.length==0){
  alert('you have to enter email and password');
}
else if(pass1.length<8){
alert('password has to be more than 8 letters or numbers');
}
else {
  mail1+='@mail.com'
  this.service.sign_up(mail1,pass1).subscribe(res=>{
    if(res=='found'){
      alert('email is taken choose another email')
    }
    else{
      this.router.navigate(['/SignIn']);
    }
  })
  
}

}
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
checkEmail(mail2:any){
  for (let i = 0; i < mail2.length; i++) {
    if(!this.charIsNumber(mail2[i])&&!this.charIsLetter(mail2[i])&&mail2[i]!='_'){
      this.valid=false
    };
   
    }
    var valid1=this.valid;
    this.valid=true;
    return valid1
}


}
