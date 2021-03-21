import { Moment } from 'moment';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { TypeDocument } from 'app/shared/model/enumerations/type-document.model';

export interface IDocument {
  id?: number;
  date?: Moment;
  intitule?: string;
  code?: string;
  version?: number;
  type?: TypeDocument;
  pieceJointeContentType?: string;
  pieceJointe?: any;
  enApplication?: boolean;
  appouve?: boolean;
  processus?: IProcessusSMI;
}

export class Document implements IDocument {
  constructor(
    public id?: number,
    public date?: Moment,
    public intitule?: string,
    public code?: string,
    public version?: number,
    public type?: TypeDocument,
    public pieceJointeContentType?: string,
    public pieceJointe?: any,
    public enApplication?: boolean,
    public appouve?: boolean,
    public processus?: IProcessusSMI
  ) {
    this.enApplication = this.enApplication || false;
    this.appouve = this.appouve || false;
  }
}
