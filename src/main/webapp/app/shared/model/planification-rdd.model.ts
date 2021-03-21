import { Moment } from 'moment';
import { Standard } from 'app/shared/model/enumerations/standard.model';

export interface IPlanificationRDD {
  id?: number;
  nRdd?: number;
  date?: Moment;
  realisee?: boolean;
  presentationContentType?: string;
  presentation?: any;
  standard?: Standard;
}

export class PlanificationRDD implements IPlanificationRDD {
  constructor(
    public id?: number,
    public nRdd?: number,
    public date?: Moment,
    public realisee?: boolean,
    public presentationContentType?: string,
    public presentation?: any,
    public standard?: Standard
  ) {
    this.realisee = this.realisee || false;
  }
}
