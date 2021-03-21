import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IAudit } from 'app/shared/model/audit.model';
import { TypeProcessus } from 'app/shared/model/enumerations/type-processus.model';

export interface IProcessusSMI {
  id?: number;
  processus?: string;
  date?: Moment;
  version?: number;
  finalite?: string;
  ficheProcessusContentType?: string;
  ficheProcessus?: any;
  type?: TypeProcessus;
  vigueur?: boolean;
  pilote?: IUser;
  audit?: IAudit;
}

export class ProcessusSMI implements IProcessusSMI {
  constructor(
    public id?: number,
    public processus?: string,
    public date?: Moment,
    public version?: number,
    public finalite?: string,
    public ficheProcessusContentType?: string,
    public ficheProcessus?: any,
    public type?: TypeProcessus,
    public vigueur?: boolean,
    public pilote?: IUser,
    public audit?: IAudit
  ) {
    this.vigueur = this.vigueur || false;
  }
}
