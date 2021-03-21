import { Moment } from 'moment';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';

export interface IPolitiqueQSE {
  id?: number;
  date?: Moment;
  axePolitiqueQSE?: string;
  objectifQSE?: string;
  vigueur?: boolean;
  processus?: IProcessusSMI;
  indicateur?: IIndicateurSMI;
}

export class PolitiqueQSE implements IPolitiqueQSE {
  constructor(
    public id?: number,
    public date?: Moment,
    public axePolitiqueQSE?: string,
    public objectifQSE?: string,
    public vigueur?: boolean,
    public processus?: IProcessusSMI,
    public indicateur?: IIndicateurSMI
  ) {
    this.vigueur = this.vigueur || false;
  }
}
