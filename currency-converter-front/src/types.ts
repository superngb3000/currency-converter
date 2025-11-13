export interface CurrencyDto {
  charCode: string;
  name: string;
}

export interface ConversionRequest {
  firstCurrency: string;
  secondCurrency: string;
  firstAmount: number | null;
  secondAmount: number | null;
}

export interface ConversionResponse {
  fromCurrency: string;
  toCurrency: string;
  fromAmount: number;
  toAmount: number;
}