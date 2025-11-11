export interface ProductFilter {
  sku?: string | null;
  name?: string | null;
  manufacturer?: string | null;
  priceMin?: number | null;
  priceMax?: number | null;
  stockMin?: number | null;
  stockMax?: number | null;
  ean?: number | null;
  source?: number | null;
  updatedAtMin: Date | null;
  updatedAtMax: Date | null;
  onlyValid?: boolean | null;

  sortBy?: string | null;
  sortDir?: 'asc' | 'desc';
  page?: number | null;
}
