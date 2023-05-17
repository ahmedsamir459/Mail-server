import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { ApiserveService } from '../service';
import { Router } from '@angular/router';
import { CurrentuseService } from '../currentuse.service';
import {Ifolder} from '../Ifolder';
import { HomepageComponent } from '../homepage/homepage.component';
import { Mail } from '../mail';
import { loadTranslations } from '@angular/localize';
import { map } from 'rxjs';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
 array1:string[]=[];
 array2:string[][]=[[]];
 option:string="";
 sorting:any

   contacts: Map<string, string[]> = new Map([
 ['',['']]
  ]);

  
  constructor(private modalservice: NgbModal,private service:ApiserveService,private http:HttpClient,private router:Router,private userservice:CurrentuseService,private homepage:HomepageComponent) {
  
   }
   closeResult: string | undefined;
  ngOnInit(): void {
    this.loadcontacts();
  }
  selectoption:number=-1
  ele:any
  displayRadioValue() {
   this.ele = document.getElementsByName('selected');
      
    for(var i = 0; i < this.ele.length; i++) {
        if(this.ele[i].checked)
        this.selectoption= this.ele[i].value;
    }
    if(this.selectoption>-1)
    this.userservice.currentaccount=this.array1[this.selectoption]
  }
addcontact(contactname:any,email:string){
  var emails = email.split(',');
this.contacts.set(contactname,emails);
console.log(emails)
this.service.addcontact(this.userservice.currentuser,contactname,emails).subscribe(res=>{
  console.log(res)
  this.loadcontacts()
});
}
addcontact2(contactname:any,emails:string[]){
this.contacts.set(contactname,emails);
console.log(emails)
this.service.addcontact(this.userservice.currentuser,contactname,emails).subscribe(res=>{
  console.log(res)
  this.loadcontacts()
});
}
loadcontacts(){
this.service.loadcontact(this.userservice.currentuser).subscribe((res:any)=>{
  if(res!=null){
    console.log(res)
    this.array1=[];
    this.array2=[[]];
    type myMap = Record<any, any>;
    const contactss: myMap = res;
    console.log(contactss)
    for (const key in contactss) {
      this.array1.push(key)
    this.array2.push(contactss[key]);
    }
  }
console.log(this.array2)
this.router.navigate(['/Contacts'])
})
}


sortoption(event:any){
  this.sorting=event.target.value;
  console.log(this.sorting)
}
sortcontact(){
  if(this.sorting=='') alert("You have to choose option")
  else if(this.sorting=="Ascendingly") 
  this.service.sortcontact(this.userservice.currentuser,"true").subscribe((res:any)=>{
    if(res!=null){
      console.log(res)
      this.array1=[];
      this.array2=[[]];
      type myMap = Record<any, any>;
      const contactss: myMap = res;
      console.log(contactss)
      for (const key in contactss) {
        this.array1.push(key)
      this.array2.push(contactss[key]);
      }
    }
  console.log(this.array2)
  })
  else if(this.sorting=="Descendingly") this.service.sortcontact(this.userservice.currentuser,"false").subscribe((res:any)=>{
    if(res!=null){
      console.log(res)
      this.array1=[];
      this.array2=[[]];
      type myMap = Record<any, any>;
      const contactss: myMap = res;
      console.log(contactss)
      for (const key in contactss) {
        this.array1.push(key)
      this.array2.push(contactss[key]);
      }
    }
  console.log(this.array2)
  })


}







editcontact(filename:any){

 
if(this.userservice.currentaccount==''){
  alert('you must choose a contact first')
}
else{
this.service.renamecontact(this.userservice.currentuser,this.userservice.currentaccount,filename).subscribe(res=>{
  console.log(res)
  const indexOfObject = this.array1.indexOf(this.userservice.currentaccount)
  console.log(indexOfObject)
  const indexOfObject1 = this.array1.indexOf(filename)
  console.log(indexOfObject1)
  alert('hena')
if(indexOfObject1==-1){
  console.log(this.array2)

  this.addcontact2(filename,this.array2[indexOfObject])
  this.deletecontact()
  this.userservice.currentaccount=''
}
else
{
  alert('already exists')
}
});
}
}


deletecontact(){
  
  if(this.userservice.currentaccount==''){
    alert('no account chosen');
  }
  else{

  const indexOfObject = this.array1.indexOf(this.userservice.currentaccount)
  const indexOfObject1 = this.array2.findIndex((object) => {
    return object === this.contacts.get(this.userservice.currentaccount);
  });
  this.array1.splice(indexOfObject,1)
  this.array2.splice(indexOfObject1,1)
  this.contacts.delete(this.userservice.currentaccount)

this.service.deletecontact(this.userservice.currentuser,this.userservice.currentaccount).subscribe(res=>{console.log(res)});
console.log(this.array1)
console.log(this.array2)
console.log(this.contacts)
this.userservice.currentaccount=''
this.loadcontacts()
this.router.navigate(['/Contacts'])
  }
}

selectoptionn(event:any){
  this.option= event.target.value;
  console.log(this.option);
  
  if(this.option=="Search"){
    document.getElementById("search")!.style.display="block";
    document.getElementById("sort")!.style.display="none";
    document.getElementById("dropdownsort")!.style.display="none";
  }
  else if(this.option=="Sort"){
    document.getElementById("search")!.style.display="none";
    document.getElementById("sort")!.style.display="block";
    document.getElementById("dropdownsort")!.style.display="block";
  
  }else{
    document.getElementById("search")!.style.display="none";
    document.getElementById("sort")!.style.display="none";
    document.getElementById("dropdownsort")!.style.display="none";
  
  }
  
  }

private getDismissReason(reason: any): string {
  if (reason === ModalDismissReasons.ESC) {
    return 'by pressing ESC';
  } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
    return 'by clicking on a backdrop';
  } else {
    return `with: ${reason}`;
  }
}
opena(content: any) {
  this.modalservice.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
    this.closeResult = `Closed with: ${result}`;
  }, (reason) => {
    this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
  });
}

openr(content: any) {

  alert(this.array1[this.selectoption])
  if(this.userservice.currentaccount==""){
    alert("No account is selected!");
  }

  else{
  this.modalservice.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
    this.closeResult = `Closed with: ${result}`;
  }, (reason) => {
    this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
  });
}
}
Selectaccount(fname:string){
this.userservice.currentaccount=fname
}
search(name:any){
this.service.searchcontacts(this.userservice.currentuser,name).subscribe(res=>{
  if(res!=null){
    console.log(res)
    this.array1=[];
    this.array2=[[]];
    type myMap = Record<any, any>;
    const contactss: myMap = res;
    console.log(contactss)
    for (const key in contactss) {
      this.array1.push(key)
    this.array2.push(contactss[key]);
    }
  }
console.log(this.array2)
})
}
}