import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, NgModule, OnInit } from '@angular/core';
import { ApiserveService } from '../service';
import { Router } from '@angular/router';
import { Mail } from '../mail';
import { FormBuilder } from '@angular/forms';
import { MailBuilder } from '../mail-builder';
import { CurrentuseService } from '../currentuse.service';
import { DomSanitizer } from '@angular/platform-browser';
import { from } from 'rxjs';
import { result } from '../Result';
import { MatIconModule } from '@angular/material/icon';
@Component({
  selector: 'app-compose',
  templateUrl: './compose.component.html',
  styleUrls: ['./compose.component.css']
})
export class ComposeComponent implements OnInit {

  constructor(private service: ApiserveService, private http: HttpClient, private router: Router, private userservice: CurrentuseService, public sanitizer: DomSanitizer) { 
    this.draftmail=userservice.draftedmail
    this.attachedFile=userservice.attachedFile
    this.attachedFileName=userservice.attachedFileName
    this.attachedFileUrl=userservice.attachedFileUrl
    if(this.draftmail.priority!=0){
      this.priority=this.draftmail.priority
      
    }
  }
  ngOnInit(): void {
  }
  to!:string
  subject!:string
  body!:string
  priority!:number
  facederror!:boolean
  attachedFile: File[] = []
  attachedFileName: String[] = []
  attachedFileUrl: any[] = []
  formData = new FormData();
  draftmail!:Mail
  c = 0
  x1: any
  x2: any
  list: any
  currentuser = this.userservice.currentuser
  mail: any
  buildmail = new MailBuilder()
  select(event: any) {
    for(var i=0; i<event.target.files.length;i++){
    this.attachedFile.push(<File>event.target.files[i])
    this.attachedFileName.push(event.target.files[i].name)
    this.x1 = URL.createObjectURL(<File>event.target.files[i])
    this.c++;
    this.x1 = <string>this.sanitizer.bypassSecurityTrustUrl(this.x1)
    this.attachedFileUrl.push(this.x1)
    }
    console.log(this.attachedFileUrl)
    console.log(this.attachedFile)
    console.log(this.attachedFileName)
    console.log(this.x1)
    console.log(this.c)

  }
  remove(i: number) {
    this.attachedFile.splice(i, 1)
    this.attachedFileName.splice(i, 1)
    this.attachedFileUrl.splice(i, 1)
    this.x1 = ""
  }
  

  send(to:any,subject:any,body:any,priority:any,attachments:any) {
    console.log(this.attachedFileName)
    this.attachedFile=attachments
    this.to=to
    this.subject=subject
    this.body=body
    this.priority=priority

    
    if(this.to.length==0||this.subject.length==0||this.priority<=0||this.priority>5||this.body.length==0){
      alert('you have to fill to ,subject,priority and body');
    }
    else{
    this.formData=new FormData()
    for (const w of this.attachedFile) {
      this.formData.append('attachment', w);
      console.log(w)
    }

    var str_array = this.to.split(',');

for(var i = 0; i < str_array.length; i++) {
   // Trim the excess whitespace.
   console.log(str_array.length)
   alert(str_array[i])
   // Add additional code here, such as:
   let date = new Date();
let formattedTime = date.toLocaleTimeString('en-US', {day:"2-digit",year:"numeric",month:"2-digit",hour: '2-digit', minute: '2-digit', second: '2-digit'});
   this.mail = this.buildmail.build_mail(this.currentuser, str_array[i], this.subject, this.body, this.priority, this.attachedFileName,formattedTime)
   console.log(this.mail)

     this.service.send_mail(this.mail).subscribe(res => {
      console.log(res)
      this.facederror=res.error
      if(!res.error){
        
        alert("This email you conatct isn't exist")}
      })
      if(!this.facederror){
      if(this.attachedFileName.length!=0){
        alert(this.mail.to)
        alert(i)
      this.service.send(this.formData,this.mail.to, this.currentuser).subscribe(res => {
        console.log(res)
    })}
  }}
  this.router.navigate(['/homepage'])
}

  }

  show() {
    console.log(this.list)
    for (let i = 0; i < this.list.length; i++) {
      this.service.getfile(this.list[i], this.currentuser).subscribe(response => {
        console.log(response)
        var binaryData = [];
        binaryData.push(response);
        window.URL.createObjectURL(new Blob(binaryData, { type: "application/pdf" }))
        this.attachedFileName.push(this.list[i])
        this.x2 = URL.createObjectURL(response)
        this.x2 = <string>this.sanitizer.bypassSecurityTrustUrl(this.x2)
        this.attachedFileUrl.push(this.x2)
        console.log(this.x2)
        console.log(this.attachedFileUrl)
      })
    }
  }
  sendtodraft(to:any,subject:any,text:any,importance:any,att:any){
    this.attachedFile=att
    this.formData=new FormData()
    for (const w of this.attachedFile) {
      this.formData.append('attachment', w);
      console.log(w)
    }
    this.to='';
    this.subject=''
    this.body=''
    this.priority=0
    this.to=to;
    this.subject=subject
    this.body=text
    this.priority=importance
   // Trim the excess whitespace.

   let date = new Date();
let formattedTime = date.toLocaleTimeString('en-US', {day:"2-digit",year:"numeric",month:"2-digit",hour: '2-digit', minute: '2-digit', second: '2-digit'});
   // Add additional code here, such as:
   this.mail = this.buildmail.build_mail(this.currentuser, this.to, this.subject, this.body, this.priority, this.attachedFileName,formattedTime)
   console.log(this.mail)
    console.log(att)
    this.service.SendToDraft(this.userservice.currentuser,this.mail).subscribe(res=>{
      console.log(res)
    })
    console.log(this.formData)
  //   this.service.send(this.formData,this.currentuser, this.currentuser).subscribe(res => {
  //     console.log(res)
  // })
  
  this.router.navigate(['/homepage'])
  
}
  
  }
