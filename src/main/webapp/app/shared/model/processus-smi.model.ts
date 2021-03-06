import { Moment } from 'moment';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { TypeProcessus } from 'app/shared/model/enumerations/type-processus.model';

export interface IProcessusSMI {
  id?: number;
  processus?: string;
  date?: Moment;
  version?: number;
  pilote?: string;
  finalite?: string;
  ficheProcessusContentType?: string;
  ficheProcessus?: any;
  type?: TypeProcessus;
  vigueur?: boolean;
  indicateurs?: IIndicateurSMI[];
}

export class ProcessusSMI implements IProcessusSMI {
  constructor(
    public id?: number,
    public processus?: string,
    public date?: Moment,
    public version?: number,
    public pilote?: string,
    public finalite?: string,
    public ficheProcessusContentType?: string,
    public ficheProcessus?: any,
    public type?: TypeProcessus,
    public vigueur?: boolean,
    public indicateurs?: IIndicateurSMI[]
  ) {
    this.vigueur = this.vigueur || false;
  }
}
