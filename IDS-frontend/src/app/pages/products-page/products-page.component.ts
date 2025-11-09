import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../shared/services/product.service';
import { ProductFilter } from '../../shared/models/product-filter.model';

@Component({
  selector: 'app-products-page',
  standalone: true,
  imports: [],
  templateUrl: './products-page.component.html',
  styleUrl: './products-page.component.scss'
})
export class ProductsPageComponent implements OnInit {

  filter: ProductFilter

  constructor(
    private productService: ProductService
  ){
    this.filter = {
     sku: null,
     name: null,
     manufacturer: null,
     priceMin: null,
     priceMax: null,
     stockMin: null,
     stockMax: null,
     ean: null,
     onlyValid: false,
     sortBy: "sku",
     sortDir: "asc",
     page: 0
    }
  }

  ngOnInit(): void {
    this.productService.getProducts(this.filter).subscribe({
      next: res => {
        console.log(res);
      },
      error: err => {
        console.log(err);
      }
    })
  }

}
