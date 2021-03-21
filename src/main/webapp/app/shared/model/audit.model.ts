import { Moment } from 'moment';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { TypeAudit } from 'app/shared/model/enumerations/type-audit.model';
import { Standard } from 'app/shared/model/enumerations/standard.model';
import { StatutAudit } from 'app/shared/model/enumerations/statut-audit.model';

export interface IAudit {
  id?: number;
  dateAudit?: Moment;
  typeAudit?: TypeAudit;
  auditeur?: string;
  standard?: Standard;
  statut?: StatutAudit;
  conclusion?: string;
  procs?: IProcessusSMI[];
  processus?: IProcessusSMI;
}

export class Audit implements IAudit {
  constructor(
    public id?: number,
    public dateAudit?: Moment,
    public typeAudit?: TypeAudit,
    public auditeur?: string,
    public standard?: Standard,
    public statut?: StatutAudit,
    public conclusion?: string,
    public procs?: IProcessusSMI[],
    public processus?: IProcessusSMI
  ) {}
}
