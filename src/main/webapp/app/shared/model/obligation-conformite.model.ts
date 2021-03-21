import { Moment } from 'moment';
import { IAction } from 'app/shared/model/action.model';
import { IUser } from 'app/core/user/user.model';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { Rubrique } from 'app/shared/model/enumerations/rubrique.model';

export interface IObligationConformite {
  id?: number;
  date?: Moment;
  rubrique?: Rubrique;
  reference?: string;
  num?: number;
  exigence?: string;
  applicable?: boolean;
  conforme?: boolean;
  statut?: number;
  observation?: string;
  origine?: string;
  action?: IAction;
  delegue?: IUser;
  processus?: IProcessusSMI;
}

export class ObligationConformite implements IObligationConformite {
  constructor(
    public id?: number,
    public date?: Moment,
    public rubrique?: Rubrique,
    public reference?: string,
    public num?: number,
    public exigence?: string,
    public applicable?: boolean,
    public conforme?: boolean,
    public statut?: number,
    public observation?: string,
    public origine?: string,
    public action?: IAction,
    public delegue?: IUser,
    public processus?: IProcessusSMI
  ) {
    this.applicable = this.applicable || false;
    this.conforme = this.conforme || false;
  }
}
