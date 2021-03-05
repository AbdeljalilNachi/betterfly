import { Moment } from 'moment';

export interface IBesoinPI {
  id?: number;
  processus?: string;
  dateIdentification?: Moment;
  piPertinentes?: string;
  pertinente?: boolean;
  priseEnCharge?: boolean;
  afficher?: boolean;
}

export class BesoinPI implements IBesoinPI {
  constructor(
    public id?: number,
    public processus?: string,
    public dateIdentification?: Moment,
    public piPertinentes?: string,
    public pertinente?: boolean,
    public priseEnCharge?: boolean,
    public afficher?: boolean
  ) {
    this.pertinente = this.pertinente || false;
    this.priseEnCharge = this.priseEnCharge || false;
    this.afficher = this.afficher || false;
  }
}
