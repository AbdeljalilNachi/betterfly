import { Moment } from 'moment';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';

export interface IBesoinPI {
  id?: number;
  dateIdentification?: Moment;
  piPertinentes?: string;
  pertinente?: boolean;
  priseEnCharge?: boolean;
  afficher?: boolean;
  processus?: IProcessusSMI;
}

export class BesoinPI implements IBesoinPI {
  constructor(
    public id?: number,
    public dateIdentification?: Moment,
    public piPertinentes?: string,
    public pertinente?: boolean,
    public priseEnCharge?: boolean,
    public afficher?: boolean,
    public processus?: IProcessusSMI
  ) {
    this.pertinente = this.pertinente || false;
    this.priseEnCharge = this.priseEnCharge || false;
    this.afficher = this.afficher || false;
  }
}
