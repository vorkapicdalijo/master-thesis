import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Brand } from '../models/brand';
import { environment } from '../../environment/environment';
import { Type } from '../models/type';
import { Category } from '../models/category';
import { Note } from '../models/note';
import { NoteType } from '../models/note-type';

@Injectable({
  providedIn: 'root'
})
export class SelectService {

  constructor(private http: HttpClient) { }


  public getBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>(environment.baseUrl+"api/selects/brands");
  }

  public getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(environment.baseUrl+"api/selects/categories");
  }

  public getTypes(): Observable<Type[]> {
    return this.http.get<Type[]>(environment.baseUrl+"api/selects/types");
  }

  public getNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(environment.baseUrl+"api/selects/notes");
  }

  public getNoteTypes(): Observable<NoteType[]> {
    return this.http.get<NoteType[]>(environment.baseUrl+"api/selects/note-types");
  }
}
