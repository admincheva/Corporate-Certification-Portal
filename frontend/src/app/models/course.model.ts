export interface Course {
  id?: number;
  title: string;
  provider: string;
  category?: string;
  price: number;
  refundable?: boolean;
  externalUrl?: string;
  status?: string;
}