import { Moment } from 'moment';
import { IAction } from 'app/shared/model/action.model';
import { IUser } from 'app/core/user/user.model';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { Situation } from 'app/shared/model/enumerations/situation.model';
import { EnumFive } from 'app/shared/model/enumerations/enum-five.model';

export interface IAnalyseSST {
  id?: number;
  date?: Moment;
  buisnessUnit?: string;
  uniteTravail?: string;
  danger?: string;
  risque?: string;
  competence?: string;
  situation?: Situation;
  frequence?: EnumFive;
  dureeExposition?: EnumFive;
  coefficientMaitrise?: EnumFive;
  gravite?: EnumFive;
  criticite?: number;
  maitriseExistante?: string;
  origine?: string;
  action?: IAction;
  delegue?: IUser;
  processus?: IProcessusSMI;
}

export class AnalyseSST implements IAnalyseSST {
  constructor(
    public id?: number,
    public date?: Moment,
    public buisnessUnit?: string,
    public uniteTravail?: string,
    public danger?: string,
    public risque?: string,
    public competence?: string,
    public situation?: Situation,
    public frequence?: EnumFive,
    public dureeExposition?: EnumFive,
    public coefficientMaitrise?: EnumFive,
    public gravite?: EnumFive,
    public criticite?: number,
    public maitriseExistante?: string,
    public origine?: string,
    public action?: IAction,
    public delegue?: IUser,
    public processus?: IProcessusSMI
  ) {}
}
