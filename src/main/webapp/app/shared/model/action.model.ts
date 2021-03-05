import { Moment } from 'moment';
import { Statut } from 'app/shared/model/enumerations/statut.model';

export interface IAction {
  id?: number;
  datePlanification?: Moment;
  action?: string;
  type?: string;
  delai?: Moment;
  avancement?: string;
  realisee?: boolean;
  critereResultat?: string;
  efficace?: boolean;
  ressourcesNecessaires?: string;
  statut?: Statut;
}

export class Action implements IAction {
  constructor(
    public id?: number,
    public datePlanification?: Moment,
    public action?: string,
    public type?: string,
    public delai?: Moment,
    public avancement?: string,
    public realisee?: boolean,
    public critereResultat?: string,
    public efficace?: boolean,
    public ressourcesNecessaires?: string,
    public statut?: Statut
  ) {
    this.realisee = this.realisee || false;
    this.efficace = this.efficace || false;
  }
}
