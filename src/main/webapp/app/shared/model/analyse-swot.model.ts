import { Moment } from 'moment';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { TypeAnalyseSWOT } from 'app/shared/model/enumerations/type-analyse-swot.model';

export interface IAnalyseSWOT {
  id?: number;
  dateIdentification?: Moment;
  description?: string;
  pilote?: string;
  type?: TypeAnalyseSWOT;
  bu?: string;
  commentaire?: string;
  afficher?: boolean;
  processus?: IProcessusSMI;
}

export class AnalyseSWOT implements IAnalyseSWOT {
  constructor(
    public id?: number,
    public dateIdentification?: Moment,
    public description?: string,
    public pilote?: string,
    public type?: TypeAnalyseSWOT,
    public bu?: string,
    public commentaire?: string,
    public afficher?: boolean,
    public processus?: IProcessusSMI
  ) {
    this.afficher = this.afficher || false;
  }
}
