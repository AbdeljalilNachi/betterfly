import { Moment } from 'moment';
import { IAction } from 'app/shared/model/action.model';
import { IUser } from 'app/core/user/user.model';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';

export interface INonConformite {
  id?: number;
  date?: Moment;
  description?: string;
  causesPotentielles?: string;
  origine?: string;
  action?: IAction;
  delegue?: IUser;
  processus?: IProcessusSMI;
}

export class NonConformite implements INonConformite {
  constructor(
    public id?: number,
    public date?: Moment,
    public description?: string,
    public causesPotentielles?: string,
    public origine?: string,
    public action?: IAction,
    public delegue?: IUser,
    public processus?: IProcessusSMI
  ) {}
}
