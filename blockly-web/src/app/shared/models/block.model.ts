export class Block {
  type: string;
  message0: string;
  args0?: [{
    type: string;
    name: string;
    check?: string;
    align?: string;
    text?: string;
    options?: [string[]]
  }];
  previousStatement?: any;
  nextStatement?: any;
  inputsInline?: boolean;
  output?: any;
  style?: string;
  tooltip?: string;
  helpUrl?: string;
}
