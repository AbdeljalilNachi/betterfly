import { Moment } from 'moment';
import { TypeAnalyseSWOT } from 'app/shared/model/enumerations/type-analyse-swot.model';

export interface IAnalyseSWOT {
  id?: number;
  processus?: string;
  dateIdentification?: Moment;
  description?: string;
  pilote?: string;
  type?: TypeAnalyseSWOT;
  bu?: string;
  commentaire?: string;
  afficher?: boolean;
}

export class AnalyseSWOT implements IAnalyseSWOT {
  constructor(
    public id?: number,
    public processus?: string,
    public dateIdentification?: Moment,
    public description?: string,
    public pilote?: string,
    public type?: TypeAnalyseSWOT,
    public bu?: string,
    public commentaire?: string,
    public afficher?: boolean
  ) {
    this.afficher = this.afficher || false;
  }
}
