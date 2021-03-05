import { Moment } from 'moment';

export interface INonConformite {
  id?: number;
  processus?: string;
  date?: Moment;
  description?: string;
  causesPotentielles?: string;
  origine?: string;
}

export class NonConformite implements INonConformite {
  constructor(
    public id?: number,
    public processus?: string,
    public date?: Moment,
    public description?: string,
    public causesPotentielles?: string,
    public origine?: string
  ) {}
}
