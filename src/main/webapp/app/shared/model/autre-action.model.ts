export interface IAutreAction {
  id?: number;
  processus?: string;
  origineAction?: string;
  origine?: string;
}

export class AutreAction implements IAutreAction {
  constructor(public id?: number, public processus?: string, public origineAction?: string, public origine?: string) {}
}
