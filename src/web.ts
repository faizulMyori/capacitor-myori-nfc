import { WebPlugin } from '@capacitor/core';

import type { MyoriNFCPlugin } from './definitions';

export class MyoriNFCWeb extends WebPlugin implements MyoriNFCPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
