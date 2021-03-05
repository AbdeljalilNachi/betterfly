import { Moment } from 'moment';
import { Situation } from 'app/shared/model/enumerations/situation.model';
import { EnumFive } from 'app/shared/model/enumerations/enum-five.model';

export interface IAnalyseEnvirommentale {
  id?: number;
  date?: Moment;
  processus?: string;
  businessUnit?: string;
  activite?: string;
  aspectEnvironnemental?: string;
  impactEnvironnemental?: string;
  competencesRequises?: string;
  situation?: Situation;
  frequence?: EnumFive;
  sensibiliteMilieu?: EnumFive;
  coefficientMaitrise?: EnumFive;
  gravite?: EnumFive;
  criticite?: number;
  maitriseExistante?: string;
  origine?: string;
}

export class AnalyseEnvirommentale implements IAnalyseEnvirommentale {
  constructor(
    public id?: number,
    public date?: Moment,
    public processus?: string,
    public businessUnit?: string,
    public activite?: string,
    public aspectEnvironnemental?: string,
    public impactEnvironnemental?: string,
    public competencesRequises?: string,
    public situation?: Situation,
    public frequence?: EnumFive,
    public sensibiliteMilieu?: EnumFive,
    public coefficientMaitrise?: EnumFive,
    public gravite?: EnumFive,
    public criticite?: number,
    public maitriseExistante?: string,
    public origine?: string
  ) {}
}
