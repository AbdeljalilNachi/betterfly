import { Moment } from 'moment';
import { TypeAction } from 'app/shared/model/enumerations/type-action.model';
import { Statut } from 'app/shared/model/enumerations/statut.model';
import { Efficace } from 'app/shared/model/enumerations/efficace.model';

export interface IAction {
  id?: number;
  datePlanification?: Moment;
  action?: string;
  type?: TypeAction;
  delai?: Moment;
  avancement?: string;
  realisee?: boolean;
  critereResultat?: string;
  ressourcesNecessaires?: string;
  statut?: Statut;
  efficace?: Efficace;
}

export class Action implements IAction {
  constructor(
    public id?: number,
    public datePlanification?: Moment,
    public action?: string,
    public type?: TypeAction,
    public delai?: Moment,
    public avancement?: string,
    public realisee?: boolean,
    public critereResultat?: string,
    public ressourcesNecessaires?: string,
    public statut?: Statut,
    public efficace?: Efficace
  ) {
    this.realisee = this.realisee || false;
  }
}
