import { Component, OnInit } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from './contact';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit{

  form!: FormGroup;
  constructor(
    private service: ContactService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', Validators.email]
    })
 
  }

  submit() {
    console.log(this.form.value)
  //   this.service.saveContact(c).subscribe( response => {
  //     console.log(response);
  //   });
  }
}
