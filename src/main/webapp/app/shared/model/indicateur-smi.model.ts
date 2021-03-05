import { Moment } from 'moment';

export interface IIndicateurSMI {
  id?: number;
  processus?: string;
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
  annee?: number;
  observation?: string;
}

export class IndicateurSMI implements IIndicateurSMI {
  constructor(
    public id?: number,
    public processus?: string,
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
    public annee?: number,
    public observation?: string
  ) {
    this.vigueur = this.vigueur || false;
  }
}
