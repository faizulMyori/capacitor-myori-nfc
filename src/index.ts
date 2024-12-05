import { registerPlugin } from '@capacitor/core';

import type { MyoriNFCPlugin } from './definitions';

const MyoriNFC = registerPlugin<MyoriNFCPlugin>('MyoriNFC', {
  web: () => import('./web').then((m) => new m.MyoriNFCWeb()),
});

export * from './definitions';
export { MyoriNFC };
