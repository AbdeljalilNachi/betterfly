import { Moment } from 'moment';

export interface IPolitiqueQSE {
  id?: number;
  processus?: string;
  date?: Moment;
  axePolitiqueQSE?: string;
  objectifQSE?: string;
  vigueur?: boolean;
  indicateur?: string;
}

export class PolitiqueQSE implements IPolitiqueQSE {
  constructor(
    public id?: number,
    public processus?: string,
    public date?: Moment,
    public axePolitiqueQSE?: string,
    public objectifQSE?: string,
    public vigueur?: boolean,
    public indicateur?: string
  ) {
    this.vigueur = this.vigueur || false;
  }
}
