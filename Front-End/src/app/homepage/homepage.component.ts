
import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { ApiserveService } from '../service';
import { Router } from '@angular/router';
import { CurrentuseService } from '../currentuse.service';
import { Ifolder } from '../Ifolder';
import { Mail } from '../mail';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ShowOnDirtyErrorStateMatcher } from '@angular/material/core';
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {


  constructor(private modalservice: NgbModal, private service: ApiserveService, private http: HttpClient, private router: Router,
    private userservice: CurrentuseService) {
    userservice.currfolder2 = "inbox";
    this.username = this.userservice.currentuser.slice(0, userservice.currentuser.indexOf("@"));
    this.page = this.userservice.page

  }
  username!: string
  currentemails: Mail[] = [];
  search = ''
  option: string = "";
  sortoption: string = "";
  searchoption: string = ""
  checkes: boolean[] = []
  mails: Mail[] = []
  closeResult: string | undefined;
  page: number = 1
  ascending = "true"
  ngOnInit(): void {

  }

  setmail(date: string) {
    for (var i = 0; i < this.userservice.currentmails.length; i++) {
      if (date == this.userservice.currentmails[i].date) {
        this.userservice.currentmail = this.userservice.currentmails[i]
      }
    }

  }
  attachedFileName: String[] = []
  attachedFileUrl: any[] = []
  selectsearchorsortoption(event: any) {

    if (this.option == 'Search') {
      this.searchoption = event.target.value
      alert(this.searchoption)
    }
    else {
      alert("sort")
      this.sortoption = event.target.value
      alert(this.sortoption)
    }

  }


  search1(inbox: any) {

    if (this.userservice.currfolder == '')
      alert('you have to select a folder')
    else {
      // currentuser:any,currentfolder:any,type:any,name:any
      alert(this.searchoption)
      this.service.search(this.userservice.currentuser, this.userservice.currfolder, this.searchoption, inbox).subscribe((res: Mail[]) => {
        this.show(res);
      })
    }
  }

  show(array: any) {
    this.page = 1
    this.currentemails = array
    console.log(this.currentemails)
    this.checkes = []
    if (this.currentemails != null) {
      for (var i = 0; i < this.currentemails.length; i++) {
        this.checkes[i] = false;
      }
    }
  }
  selectasc(event: any) {
    if (event.target.value == "Descendingly")
      this.ascending = "false"
    else if (event.target.value == "Ascendingly")
      this.ascending = "true"
    else alert("please choose valid option")
  }

  selectoption(event: any) {
    this.option = event.target.value;
    console.log(this.option);

    if (this.option == "Search") {
      document.getElementById("dropdownoptsearch")!.style.display = "block";
      document.getElementById("dropdownoptsort")!.style.display = "none";
      document.getElementById("search")!.style.display = "block";
      document.getElementById("sort")!.style.display = "none";
      document.getElementById("dropdownsort")!.style.display = "none";
    }
    else if (this.option == "Sort") {
      document.getElementById("dropdownoptsearch")!.style.display = "none";
      document.getElementById("dropdownoptsort")!.style.display = "block";
      document.getElementById("search")!.style.display = "none";
      document.getElementById("sort")!.style.display = "block";
      document.getElementById("dropdownsort")!.style.display = "block";

    } else {
      document.getElementById("dropdownoptsearch")!.style.display = "none";
      document.getElementById("dropdownoptsort")!.style.display = "none";
      document.getElementById("search")!.style.display = "none";
      document.getElementById("sort")!.style.display = "none";
      document.getElementById("dropdownsort")!.style.display = "none";

    }

  }

  getValuesChecked() {
    this.currentemails.filter((x, index) => this.checkes[index])
      .map(x => x.from).join(",")
    console.log(this.checkes)
  }
  sort() {
    alert(this.sortoption)
    if (this.sortoption != '') {
      alert(this.sortoption);
      if (this.userservice.currfolder == '')
        alert('you have to select a folder')
      else {
        this.service.sort(this.userservice.currentuser, this.userservice.currfolder, this.sortoption, this.ascending).subscribe((res: Mail[]) => {
          this.show(res);
          console.log(res);
          alert(res)
        })
      }
    }
  }






  bulkdelete() {

    this.mails = []
    for (let i = 0; i < this.checkes.length; i++) {
      if (this.checkes[i] == true) {
        this.mails.push(this.currentemails[i])
      }
    }
    this.service.deletemail(this.mails, this.userservice.currentuser, this.userservice.currfolder).subscribe(res => {
      console.log(res)
      this.userservice.reload()
    })
  }
  bulkmove(filename: any) {
    var flag = true
    for (let entry of this.userservice.currentfolders) {
      if (filename == entry.name) {
        flag = false;
      }
    }
    if (flag == true) {
      alert('filename is not found')
    }
    else {
      for (let i = 0; i < this.checkes.length; i++) {
        if (this.checkes[i] == true) {
          this.mails.push(this.currentemails[i])
        }
      }
      console.log(this.userservice.currfolder)
      console.log(filename)
      this.service.movemails(this.mails, this.userservice.currentuser, this.userservice.currfolder, filename).subscribe(res => {
        console.log(res)
      })
    }
    this.userservice.reload()
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
    this.modalservice.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }
  restore() {
    if (this.userservice.currfolder != 'trash') {
      alert('you can only restore from trash')
    }
    else {
      this.bulkmove('restored')
    }
  }

  TableDataChange(event: any) {
    this.page = event;


  }

}