import { IAction } from 'app/shared/model/action.model';

export interface IConstat {
  id?: number;
  processus?: string;
  audit?: string;
  constat?: string;
  type?: string;
  origine?: string;
  action?: IAction;
}

export class Constat implements IConstat {
  constructor(
    public id?: number,
    public processus?: string,
    public audit?: string,
    public constat?: string,
    public type?: string,
    public origine?: string,
    public action?: IAction
  ) {}
}
