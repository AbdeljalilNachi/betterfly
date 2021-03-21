import { IAction } from 'app/shared/model/action.model';
import { IUser } from 'app/core/user/user.model';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { IAudit } from 'app/shared/model/audit.model';
import { TypeConstatAudit } from 'app/shared/model/enumerations/type-constat-audit.model';

export interface IConstatAudit {
  id?: number;
  type?: TypeConstatAudit;
  constat?: string;
  origine?: string;
  action?: IAction;
  delegue?: IUser;
  processus?: IProcessusSMI;
  audit?: IAudit;
}

export class ConstatAudit implements IConstatAudit {
  constructor(
    public id?: number,
    public type?: TypeConstatAudit,
    public constat?: string,
    public origine?: string,
    public action?: IAction,
    public delegue?: IUser,
    public processus?: IProcessusSMI,
    public audit?: IAudit
  ) {}
}
