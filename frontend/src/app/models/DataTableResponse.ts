export interface DataTableResponseData<T> {
  content: T[];
  totalElements: number;
  empty: boolean;
  first: boolean;
  size: number;
  number: number;
  numberOfElements: number;
  totalPages: number;
}
