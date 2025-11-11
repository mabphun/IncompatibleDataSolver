import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { ProductFilter } from '../models/product-filter.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private readonly URL: string = "http://localhost:8080"

  constructor(
    private http: HttpClient
  ){}
  
  public getProducts(filter: ProductFilter): Observable<Product[]>{
    return this.http.post<Product[]>(this.URL + "/products", filter)
  }

  public uploadFile(formData: FormData): Observable<string[]> {
    return this.http.post<string[]>(this.URL + "/upload", formData)
  }
}
