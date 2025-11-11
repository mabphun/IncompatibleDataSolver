import { Component } from '@angular/core';
import { ProductService } from '../../shared/services/product.service';
import { ProductFilter } from '../../shared/models/product-filter.model';
import { PaginatorModule } from 'primeng/paginator';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { Product } from '../../shared/models/product.model';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { ButtonModule } from "primeng/button";
import { CalendarModule } from 'primeng/calendar';
import { CheckboxModule } from 'primeng/checkbox';

@Component({
  selector: 'app-products-page',
  standalone: true,
  imports: [
    TableModule,
    PaginatorModule,
    InputTextModule,
    CardModule,
    ButtonModule,
    CalendarModule,
    CheckboxModule
],
  templateUrl: './products-page.component.html',
  styleUrl: './products-page.component.scss'
})
export class ProductsPageComponent /*implements OnInit*/ {

  filter: ProductFilter
  products: Product[] = []

  rowsPerPage = 10
  first: number = 0
  totalRecords: number = 0

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
     source: null,
     updatedAtMin: null,
     updatedAtMax: null,
     onlyValid: false,
     sortBy: "sku",
     sortDir: "asc",
     page: 0
    }
  }

  getProducts(): void{
    this.productService.getProducts(this.filter).subscribe({
      next: (res: any) => {
        //console.log(res);
        this.products = []
        res.content.map((r: Product) => {
          this.products.push(r)
        })
        this.totalRecords = res.totalElements
      },
      error: err => {
        console.log(err);
      }
    })
  }

  loadProducts($event: TableLazyLoadEvent) {
    console.log($event);
    this.filter.page = (($event.first || 0) / 10)
    this.filter.sortBy = $event.sortField?.toString()
    this.filter.sortDir = $event.sortOrder === 1 ? "asc" : "desc"
    this.getProducts()
  }

  clearFilters(){
    this.filter.sku = null
    this.filter.name = null
    this.filter.manufacturer = null
    this.filter.priceMin = null
    this.filter.priceMax = null
    this.filter.stockMin = null
    this.filter.stockMax = null
    this.filter.ean = null
    this.filter.source = null
    this.filter.updatedAtMin = null
    this.filter.updatedAtMax = null
    this.filter.onlyValid = null
    this.getProducts()
  }

}
