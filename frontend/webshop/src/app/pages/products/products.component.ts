import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Product } from '../../models/product';
import { MatRippleModule } from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { SizePrice } from '../../models/size-price';
import { AuthService } from '../../services/auth.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ProductFormDialogComponent } from '../../dialogs/product-form-dialog/product-form-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../../environment/environment';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { SelectService } from '../../services/select.service';
import { Brand } from '../../models/brand';
import { Type } from '../../models/type';
import { Category } from '../../models/category';
import { Observable, map, startWith } from 'rxjs';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [
    MatCardModule,
    MatButtonModule,
    MatRippleModule,
    MatIconModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    AsyncPipe,
  ],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss',
})
export class ProductsComponent implements OnInit {
  isAdmin: boolean = false;
  isLoaded: boolean = false;
  products: Product[] = [];

  brands: Brand[] = [];
  types: Type[] = [];
  categories: Category[] = [];

  filteredCategories: Observable<Category[]>;
  filteredBrands: Observable<Brand[]>;
  filteredTypes: Observable<Type[]>;

  filterForm: FormGroup;
  isFormEmpty: boolean = true;
  areProductsFiltered: boolean = false;
  categoryId: any;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,
    private selectService: SelectService,
  ) {}

  ngOnInit(): void {

    this.filterForm = this.fb.group({
      brand: new FormControl(''),
      type: new FormControl(''),
      category: new FormControl(''),
    });

    this.route.params.subscribe(params => {
      this.categoryId = params['categoryId'];
      if (this.categoryId){
        this.getProducts(null, params['categoryId'], null);
      }
      else {
        this.getProducts();
      }
    })

    this.isAdmin = this.authService.getUserRoles().includes('admin');

    this.selectService.getBrands().subscribe(brands => {
      this.brands = brands;

      this.filteredBrands = this.filterForm.get('brand')!.valueChanges.pipe(
        startWith(''),
        map(brand => (brand ? this._filterBrands(brand.name ? brand.name : brand) : this.brands.slice())),
      );
    });
    this.selectService.getCategories().subscribe(categories => {
      this.categories = categories;
      if(this.categoryId) {
        this.filterForm.get('category').setValue(this.categories.find(category => category.categoryId == this.categoryId));
        this.areProductsFiltered = true;
      }  
      this.filteredCategories = this.filterForm.get('category')!.valueChanges.pipe(
        startWith(''),
        map(category => (category ? this._filterCategories(category.name ? category.name : category) : this.categories.slice())),
      );
    });
    this.selectService.getTypes().subscribe(types => {
      this.types = types;

      this.filteredTypes = this.filterForm.get('type')!.valueChanges.pipe(
        startWith(''),
        map(type => (type ? this._filterTypes(type.name ? type.name : type) : this.types.slice())),
      );
    });

    this.filterForm.valueChanges.subscribe(res => {
      this.checkIsFormEmpty();
    })
  }

  private _filterBrands(value: string): Brand[] {
    const filterValue = value.toLowerCase();

    return this.brands.filter(brand => brand.name.toLowerCase().includes(filterValue));
  }

  private _filterCategories(value: string): Category[] {
    const filterValue = value.toLowerCase();

    return this.categories.filter(category => category.name.toLowerCase().includes(filterValue));
  }

  private _filterTypes(value: string): Type[] {
    const filterValue = value.toLowerCase();

    return this.types.filter(type => type.name.toLowerCase().includes(filterValue));
  }

  displayFn(value: any): string {
    return value?.name;
  }

  public getProducts(brandId?: number, categoryId?: number, typeId?: number) {
    this.isLoaded = false;
    this.productService.getProducts(brandId, categoryId, typeId).subscribe((res) => {
      this.products = res;
      this.isLoaded = true;
    });
  }

  public filterProducts() {
    this.areProductsFiltered = true;
    let brand: Brand = this.filterForm.get('brand')?.value;
    let category: Category = this.filterForm.get('category')?.value;
    let type: Type = this.filterForm.get('type')?.value;

    this.getProducts(brand.brandId, category.categoryId, type.typeId);
  }

  public clearFilter() {
    this.filterForm.get('brand').setValue('');
    this.filterForm.get('category').setValue('');
    this.filterForm.get('type').setValue('');
    this.areProductsFiltered = false;
    this.router.navigateByUrl('/products');
    this.getProducts();
  }

  public openProductDetails(productId: number) {
    this.router.navigateByUrl(`product-details/${productId}`);
  }

  public checkIsFormEmpty() {
    let brand = this.filterForm.get('brand')?.value;
    let category = this.filterForm.get('category')?.value;
    let type = this.filterForm.get('type')?.value;

    this.isFormEmpty = typeof brand == 'string' && typeof category == 'string' && typeof type == 'string';

  }

  public getCategoryName() {
    return this
  }

  public getLowestSizePrice(sizePrices: SizePrice[]) {
    if (sizePrices) {
      if (sizePrices.length === 0) {
        return null;
      }
      let lowestPriceItem = sizePrices[0];

      for (let i = 1; i < sizePrices.length; i++) {
        if (sizePrices[i].price < lowestPriceItem.price) {
          lowestPriceItem = sizePrices[i];
        }
      }

      return lowestPriceItem.price;
    }
    return null;
  }

  addProduct() {
    const dialogRef = this.dialog.open(ProductFormDialogComponent, {
      data: {
        isEdit: false,
      },
      position: {
        top: '100px',
      },
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((res) => {
      if (res)
        this.openSnackBar('New Fragrance added!', 'Dismiss');
      this.getProducts();
    });
  }

  public openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, { duration: 3000 });
  }

  public getImageUrl(imageName: string) {
    return environment.imageBaseUrl + imageName;
  }
}
