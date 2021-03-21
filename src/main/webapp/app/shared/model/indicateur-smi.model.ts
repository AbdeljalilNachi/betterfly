import { Moment } from 'moment';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';

export interface IIndicateurSMI {
  id?: number;
  dateIdentification?: Moment;
  indicateur?: string;
  formuleCalcul?: string;
  cible?: number;
  seuilTolerance?: number;
  unite?: string;
  periodicite?: string;
  responsableCalcul?: string;
  observations?: string;
  vigueur?: boolean;
  processus?: IProcessusSMI;
}

export class IndicateurSMI implements IIndicateurSMI {
  constructor(
    public id?: number,
    public dateIdentification?: Moment,
    public indicateur?: string,
    public formuleCalcul?: string,
    public cible?: number,
    public seuilTolerance?: number,
    public unite?: string,
    public periodicite?: string,
    public responsableCalcul?: string,
    public observations?: string,
    public vigueur?: boolean,
    public processus?: IProcessusSMI
  ) {
    this.vigueur = this.vigueur || false;
  }
}
