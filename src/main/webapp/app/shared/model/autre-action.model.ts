import { IAction } from 'app/shared/model/action.model';
import { IUser } from 'app/core/user/user.model';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';

export interface IAutreAction {
  id?: number;
  origineAction?: string;
  origine?: string;
  action?: IAction;
  delegue?: IUser;
  processus?: IProcessusSMI;
}

export class AutreAction implements IAutreAction {
  constructor(
    public id?: number,
    public origineAction?: string,
    public origine?: string,
    public action?: IAction,
    public delegue?: IUser,
    public processus?: IProcessusSMI
  ) {}
}
