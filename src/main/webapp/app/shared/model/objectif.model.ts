import { IAction } from 'app/shared/model/action.model';
import { IUser } from 'app/core/user/user.model';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';

export interface IObjectif {
  id?: number;
  axedelapolitiqueqse?: string;
  objectifqse?: string;
  origine?: string;
  action?: IAction;
  delegue?: IUser;
  processus?: IProcessusSMI;
  indicateur?: IIndicateurSMI;
}

export class Objectif implements IObjectif {
  constructor(
    public id?: number,
    public axedelapolitiqueqse?: string,
    public objectifqse?: string,
    public origine?: string,
    public action?: IAction,
    public delegue?: IUser,
    public processus?: IProcessusSMI,
    public indicateur?: IIndicateurSMI
  ) {}
}
