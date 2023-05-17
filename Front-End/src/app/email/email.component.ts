import { Component, OnInit } from '@angular/core';
import { ComposeComponent } from '../compose/compose.component';
import { CurrentuseService } from '../currentuse.service';
import { Mail } from '../mail';
import { ApiserveService } from '../service';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Router } from '@angular/router';
import { MailBuilder } from '../mail-builder';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {
  currentemails:Mail[]=[];
  thatmail!:Mail
  
Reply!:string
list:any
x2:any
mails:Mail[]=[]
attachedFile: File[] = []
  attachedFileName: String[] = []
  attachedFileUrl: any[] = []
  constructor(private service: ApiserveService, private http: HttpClient, private router: Router, private userservice: CurrentuseService, public sanitizer: DomSanitizer) { 
    this.thatmail=this.userservice.currentmail
      this.list=this.thatmail.attachment
      if(userservice.Reply=='sent'){this.Reply="Reply"}
      else if(userservice.Reply=='draft'){this.Reply="edit"}
      else{this.Reply='Compose'}
      this.show()
  }

  ngOnInit(): void {
  }
  edit(){
    if(this.userservice.Reply=='sent'){
        this.router.navigate(['/compose'])
    }
    else if(this.userservice.Reply=='draft'){
    let build =new MailBuilder()
    this.userservice.draftedmail=build.build_mail_by_mail(this.thatmail)
    this.userservice.attachedFile=this.attachedFile
    this.userservice.attachedFileName=this.attachedFileName
    this.userservice.attachedFileUrl=this.attachedFileUrl
  
    this.mails.push(this.userservice.draftedmail)
    this.service.deletemail(this.mails,this.userservice.currentuser,'draft').subscribe(res=>console.log(res))
    this.service.deletemail(this.mails,this.userservice.currentuser,'trash').subscribe(res=>console.log(res))
    console.log(this.userservice.draftedmail)
    this.router.navigate(['/compose'])
    }
    else{
      this.router.navigate(['/compose'])
    }

    
  }
  show() {    
    console.log(this.list)
    for (let i = 0; i < this.list.length; i++) {
      this.service.getfile(this.list[i], this.thatmail.from).subscribe(response => {
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




}