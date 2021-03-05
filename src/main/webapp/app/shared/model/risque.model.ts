import { Moment } from 'moment';
import { TypeRisque } from 'app/shared/model/enumerations/type-risque.model';
import { EnumFive } from 'app/shared/model/enumerations/enum-five.model';
import { Traitement } from 'app/shared/model/enumerations/traitement.model';

export interface IRisque {
  id?: number;
  processus?: string;
  dateIdentification?: Moment;
  description?: string;
  causePotentielle?: string;
  effetPotentiel?: string;
  type?: TypeRisque;
  gravite?: EnumFive;
  probabilite?: EnumFive;
  criticite?: number;
  traitement?: Traitement;
  commentaire?: string;
  origine?: string;
}

export class Risque implements IRisque {
  constructor(
    public id?: number,
    public processus?: string,
    public dateIdentification?: Moment,
    public description?: string,
    public causePotentielle?: string,
    public effetPotentiel?: string,
    public type?: TypeRisque,
    public gravite?: EnumFive,
    public probabilite?: EnumFive,
    public criticite?: number,
    public traitement?: Traitement,
    public commentaire?: string,
    public origine?: string
  ) {}
}
