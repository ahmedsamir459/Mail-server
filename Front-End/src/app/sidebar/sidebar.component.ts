import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { ApiserveService } from '../service';
import { Router } from '@angular/router';
import { CurrentuseService } from '../currentuse.service';
import { Ifolder } from '../Ifolder';
import { HomepageComponent } from '../homepage/homepage.component';
import { Mail } from '../mail';
import { FormBuilder } from '@angular/forms';
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  folders: Ifolder[] = [{ name: "inbox", mailIds: 0 }];
  folder: Ifolder = { name: '', mailIds: 0 };
  newFolderName: String = "";
  closeResult: string | undefined;
  constructor(private modalservice: NgbModal, private service: ApiserveService, private http: HttpClient, private router: Router, private userservice: CurrentuseService, private homepage: HomepageComponent) {
    this.folders = [{ name: "inbox", mailIds: 0 }, { name: "sent", mailIds: 0 }, { name: "draft", mailIds: 0 }, { name: "trash", mailIds: 0 }, { name: 'restored', mailIds: 0 }]
    this.reload();

  }
  ngOnInit(): void {
  }

  gocompose() {
    this.userservice.draftedmail = new Mail()
    if (this.userservice.draftedmail.priority == 0) {
      this.userservice.draftedmail.priority
    }
    this.router.navigate(['/compose'])
  }
  reload() {
    this.homepage.currentemails = []
    this.userservice.currfolder = ''
    this.userservice.currfolder2 = ''
    this.service.reload(this.userservice.currentuser).subscribe((res: any) => {
      console.log(res);
      type myMap = Record<number, any>;
      const map: myMap = res;
      this.folders = [{ name: "inbox", mailIds: 0 }, { name: "sent", mailIds: 0 }, { name: "draft", mailIds: 0 }, { name: "trash", mailIds: 0 }, { name: 'restored', mailIds: 0 }]

      for (const key in map) {
        if (key == 'trash') {
          var index = 3;

          this.folders[index].mailIds = map[key]
        }
        else if (key == 'inbox') {
          var index = 0
          this.folders[index].mailIds = map[key]
        }
        else if (key == 'sent') {
          var index = 1;

          this.folder = this.folders[index]
          console.log(this.folders)
          this.folders[index].mailIds = map[key]
        }
        else if (key == 'draft') {
          var index = 2
          this.folders[index].mailIds = map[key]
        }
        else if (key == 'contacts') {

        } else if (key == 'restored') {
          var index = 4
          this.folders[index].mailIds = map[key]
        }

        else {
          this.folder = { name: key, mailIds: map[key] }
          this.folders.push(this.folder)
        }
      }
    })
    this.userservice.currentfolders = this.folders
  }
  addfile(filename: string) {
    if (this.folders.length == 8) {
      alert('you can not make any more folders')
    }
    else {
      var flag = true;


      for (const w of this.folders) {
        if (filename == w.name) {
          flag = false;
        }
      }
      if (flag == false) {
        alert('there is a file with the same name');
      }
      else {
        for (const l of filename) {
          if (!this.charIsLetter(l) && !this.charIsNumber(l)) {
            flag = false;
            break;
          }
        }
        if (flag == false) {
          alert('file name can only contain letters and numbers');
        }
        else {
          this.folders.push({ "name": filename, "mailIds": [] })
          this.service.addfile(this.userservice.currentuser, filename).subscribe(res => {
            console.log(res);
          });
        }
      }
    }
  }


  Selectfolder(fname: string) {
      this.service.show(fname,this.userservice.currentuser).subscribe((res:Mail[])=>{
        
        this.userservice.currentmails=res;
        this.userservice.currfolder=fname;
        this.userservice.currfolder2=fname;
        this.userservice.Reply=fname
        this.userservice.page=1
        for (var x of this.folders) {
          document.getElementById(x.name)!.style.backgroundColor = "darkgray";
        }
        document.getElementById(this.userservice.currfolder2)!.style.backgroundColor = "rgba(128, 255, 0, 0.795)";
    this.homepage.show(this.userservice.currentmails);
      });
    
  }

  Deletefolder() {
    console.log(this.userservice.currfolder);
    if (this.userservice.currfolder == "") {
      alert("No Folder is selected!");
    }
    else if (this.userservice.currfolder == "inbox" || this.userservice.currfolder == "draft" || this.userservice.currfolder == "sent" || this.userservice.currfolder == "trash"||this.userservice.currfolder=="restored") {
      alert("The Selected Folder Cannot Be Deleted");
    }
    else {
      const indexOfObject = this.folders.findIndex((object) => {
        return object.name === this.userservice.currfolder;
      });
      this.service.deletefolder(this.userservice.currentuser, this.userservice.currfolder).subscribe(res => {
        console.log(res)
      })
      this.folders.splice(indexOfObject, 1);
      this.userservice.currfolder = "";
      alert("Folder Deleted Successfully");

    }
  }
  Renamefolder(fname: string) {
    this.service.renamefile(this.userservice.currentuser, this.userservice.currfolder, fname).subscribe(res => {
      console.log(this.userservice.currfolder);
      const indexOfObject = this.folders.findIndex((object) => {
        return object.name === this.userservice.currfolder;
      });
      this.folders[indexOfObject].name = fname;
      this.userservice.currfolder = "";
    })


  }

  opena(content: any) {
    this.modalservice.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }
  openr(content: any) {
    if (this.userservice.currfolder == "") {
      alert("No Folder is selected!");
    }
    else if (this.userservice.currfolder == "inbox" || this.userservice.currfolder == "draft" || this.userservice.currfolder == "sent" || this.userservice.currfolder == "trash"||this.userservice.currfolder=="restored") {
      alert("You Cannot Change Name of this Folder");
    }
    else {
      this.modalservice.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
        this.closeResult = `Closed with: ${result}`;
      }, (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      });
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
  charIsNumber(char: any) {
    return /^\d$/.test(char);
  }
  charIsLetter(char: any) {
    if (typeof char !== 'string') {
      return false;
    }

    return /^[a-zA-Z]+$/.test(char);
  }

}