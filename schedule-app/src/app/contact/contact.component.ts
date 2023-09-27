import { Component, OnInit } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from './contact';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ContactDetailsComponent } from '../contact-details/contact-details.component';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit{

  form!: FormGroup;
  contacts: Contact[] = [];
  columns = ['picture','id', 'name', 'email', 'favorite'];
  
  totalElements?: number = 0;
  page?: number = 0;
  size: number = 10;
  pageSizeOptions: number[] = [10];
  
  
  constructor(
    private service: ContactService,
    private fb: FormBuilder,
    private dialog: MatDialog,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.formComponent();
    
    this.listContacts(this.page, this.size);
 
  }

  listContacts(page:any = "0", size:any = "10") {
    this.service.listContactsService(page, size).subscribe(response => {
      this.contacts = response.content || [];
      this.totalElements = response.totalElements;
      this.page = response.number;
    })
  }

  formComponent() {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  favoriteContact(contact: Contact) {
    this.service.favoriteContactService(contact).subscribe(response => {
      contact.favorite = !contact.favorite;
    });   
  }

  submit() {
    const formValues = this.form.value;
    const contact: Contact = new Contact(formValues.name, formValues.email);
    this.service.saveContact(contact).subscribe( response => {
      this.listContacts();
      this.snackbar.open('Contato adicionado!', "Sucesso", {duration:2000})
      this.form.reset();
    });
  }

  uploadPicture(event: any, contact: Contact) {
    const files = event.target.files;
    if(files) {
      const picture = files[0];
      const formData: FormData = new FormData();
      formData.append("picture", picture);
      this.service.uploadPictureService(contact, formData).subscribe(response => {
        this.listContacts();
      });
    }
  }

  visualizeContact(contact: Contact) {
    this.dialog.open(ContactDetailsComponent, {
      width:'400px',
      height: '450px',
      data: contact
    })
  }

  paginate(event: PageEvent) {
    this.page = event.pageIndex;
    this.listContacts(this.page, this.size);
  }

  delete(contact:Contact) {
    this.service.delete(contact).subscribe(response => {
      this.listContacts();
    });
  }
}
