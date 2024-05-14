import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Review } from '../models/review';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }

  public addReview(review: Review): Observable<Review> {
    return this.http.post<Review>('http://localhost:8083/api/reviews/add',
      review
    );
  }
}
