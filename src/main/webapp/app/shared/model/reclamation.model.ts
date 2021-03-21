import { Moment } from 'moment';
import { IAction } from 'app/shared/model/action.model';
import { IUser } from 'app/core/user/user.model';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';

export interface IReclamation {
  id?: number;
  date?: Moment;
  description?: string;
  justifiee?: boolean;
  client?: string;
  piecejointeContentType?: string;
  piecejointe?: any;
  origine?: string;
  action?: IAction;
  delegue?: IUser;
  processus?: IProcessusSMI;
}

export class Reclamation implements IReclamation {
  constructor(
    public id?: number,
    public date?: Moment,
    public description?: string,
    public justifiee?: boolean,
    public client?: string,
    public piecejointeContentType?: string,
    public piecejointe?: any,
    public origine?: string,
    public action?: IAction,
    public delegue?: IUser,
    public processus?: IProcessusSMI
  ) {
    this.justifiee = this.justifiee || false;
  }
}
