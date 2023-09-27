import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Contact } from './contact/contact';
import { environment } from 'src/environment/environment';
import { ContactPage } from './contact/contactPage';
@Injectable({
  providedIn: 'root'
})
export class ContactService {

  url: string = environment.apiBaseUrl;


  constructor(
    private http: HttpClient
  ) { }

  saveContact (contact: Contact) : Observable<Contact> {
    return this.http.post<Contact>(this.url, contact);
  }

  listContactsService(page: any, size: any): Observable<ContactPage> {
    const params = new HttpParams()
    .set('page', page)
    .set('size', size)
    return this.http.get<any>(`${this.url}?${params.toString()}`);
  }
  favoriteContactService(contact: Contact): Observable<any> {
    return this.http.patch(`${this.url}/${contact.id}/favorite`, null)
  }

  uploadPictureService(contact: Contact, formData: FormData): Observable<any> {
    return this.http.put(`${this.url}/${contact.id}/picture`, formData, {responseType: 'blob'});
  }

  delete(contact: Contact) : Observable<any> {
    return this.http.delete(`${this.url}/${contact.id}`);
  }
}
